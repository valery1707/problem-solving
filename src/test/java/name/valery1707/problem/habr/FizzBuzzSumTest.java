package name.valery1707.problem.habr;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.function.Try;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("scwtestingjunit5_JUnitMixeduseofJUnitversions")
class FizzBuzzSumTest {

    private static final String SOURCE = "/habr/fizzBuzzSum.csv";

    private static final FizzBuzzSum INSTANCE = new FizzBuzzSum();

    private enum Variant {
        naive(INSTANCE::naive),
        simple(INSTANCE::simple),
        summationDancing(INSTANCE::summationDancing),
        pin2t(INSTANCE::pin2t),
        igolikov(INSTANCE::igolikov),
        rombell(INSTANCE::rombell),
        ;
        private final Check check;

        Variant(Check check) {this.check = check;}
    }

    private static final Set<String> TOO_BOUNDED = Set.of(new String[]{
        "pin2t(0, 100000, 20)",
        "rombell(0, 1000, 20)",
        "rombell(0, 100000, 20)",
    });

    @ParameterizedTest(name = "[{index}] Run {0} variant with range [{2} .. {3}] and limit {4}")
    @MethodSource("test1")
    void test1(String name, Check check, int min, int max, int lim, String expected) throws IOException {
        name = String.format("%s(%d, %d, %d)", name, min, max, lim);
        try (var stream = new ByteArrayOutputStream()) {
            var out = new PrintStream(stream, false, UTF_8);
            assertThatCode(() -> check.exec(out, min, max, lim)).doesNotThrowAnyException();
            out.flush();
            if (TOO_BOUNDED.contains(name)) {
                assertThat(stream.toString(UTF_8)).as(name).isNotEqualToNormalizingWhitespace(expected);
            } else {
                assertThat(stream.toString(UTF_8)).as(name).isEqualToNormalizingWhitespace(expected);
            }
        }
    }

    private static Stream<Arguments> test1() {
        var lines = Optional.ofNullable(FizzBuzzSumTest.class.getResource(SOURCE))
            .flatMap(it -> Try.call(it::toURI).toOptional())
            .map(Path::of)
            .filter(Files::exists)
            .flatMap(it -> Try.call(() -> Files.lines(it, UTF_8)).toOptional())
            .stream().flatMap(identity());
        return lines
            .skip(1)
            .map(it -> it.split("\\|"))
            .map(it -> new Object[]{parseInt(it[0]), parseInt(it[1]), parseInt(it[2]), it[3].replace(",", lineSeparator())})
            .flatMap(args -> Stream.of(Variant.values()).map(variant ->
                arguments(variant.name(), variant.check, args[0], args[1], args[2], args[3])
            ));
    }

    @FunctionalInterface
    private interface Check {

        void exec(PrintStream out, int min, int max, int lim);

    }

}
