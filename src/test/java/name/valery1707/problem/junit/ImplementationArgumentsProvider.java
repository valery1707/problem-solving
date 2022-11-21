package name.valery1707.problem.junit;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

@SuppressWarnings("scwtestingjunit5_JUnitMixeduseofJUnitversions")
public class ImplementationArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<ImplementationSource> {

    private static final Set<Function<ImplementationSource, Stream<Annotation>>> ARGS_PROVIDERS = Stream.<Function<ImplementationSource, Annotation[]>>of(
            ImplementationSource::csv,
            ImplementationSource::csvFiles,
            ImplementationSource::enums,
            ImplementationSource::method,
            ImplementationSource::values
        )
        .map(it -> it.andThen(Stream::of))
        .collect(toUnmodifiableSet());

    private Map<String, Object> variants;
    private Set<ArgumentsProvider> args;

    @Override
    public void accept(ImplementationSource config) {
        this.variants = Stream.of(config.implementation())
            .flatMap(impl -> Stream.of(impl.getEnumConstants()))
            .collect(toUnmodifiableMap(Enum::name, identity()));
        this.args = ARGS_PROVIDERS.stream()
            .flatMap(arg -> arg.apply(config))
            .filter(arg -> arg.annotationType().isAnnotationPresent(ArgumentsSource.class))
            .map(ImplementationArgumentsProvider::buildArgumentsProvider)
            .collect(toUnmodifiableSet());
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return variants.entrySet().stream()
            .flatMap(variant -> args.stream()
                .flatMap(arg -> provideArgumentsImpl(arg, context))
                .map(arg -> prepend(variant, arg))
            );
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private static ArgumentsProvider buildArgumentsProvider(Annotation it) {
        var providerType = it.annotationType().getAnnotation(ArgumentsSource.class).value();
        var provider = makeInstance(providerType);
        if (provider instanceof AnnotationConsumer<?>) {
            ((AnnotationConsumer<Annotation>) provider).accept(it);
        }
        return provider;
    }

    @NotNull
    private static <T> T makeInstance(Class<? extends T> type) {
        try {
            Constructor<? extends T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JUnitException("Failed to instance " + type, e);
        }
    }

    @NotNull
    private static Stream<? extends Arguments> provideArgumentsImpl(ArgumentsProvider provider, ExtensionContext context) {
        try {
            return provider.provideArguments(context);
        } catch (Exception e) {
            throw new JUnitException("Failed to provide arguments", e);
        }
    }

    @NotNull
    private static Arguments prepend(Map.Entry<String, Object> variant, Arguments arg) {
        return Arguments.of(combine(
            new Object[]{Implementation.of(variant)},
            arg.get()
        ));
    }

    @NotNull
    private static Object[] combine(Object[] lhs, Object[] rhs) {
        Object[] copy = Arrays.copyOf(lhs, lhs.length + rhs.length);
        int i = lhs.length;
        for (Object o : rhs) {
            copy[i++] = o;
        }
        return copy;
    }

}
