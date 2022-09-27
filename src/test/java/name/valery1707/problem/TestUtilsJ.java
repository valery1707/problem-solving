package name.valery1707.problem;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public final class TestUtilsJ {

    private TestUtilsJ() {}

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <I> Map<String, I> findVariants(Class<I> type) {
        return Stream.of(type.getDeclaredClasses())
            .filter(Class::isEnum)
            .filter(type::isAssignableFrom)
            .flatMap(it -> Stream.of(it.getEnumConstants()))
            .collect(toUnmodifiableMap(it -> ((Enum) it).name(), it -> (I) it));
    }

}
