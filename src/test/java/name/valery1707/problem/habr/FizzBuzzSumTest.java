package name.valery1707.problem.habr;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
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

@SuppressWarnings("scwtestingjunit5_JUnitMixeduseofJUnitversions")
class FizzBuzzSumTest {

    private static final String SOURCE = "/habr/fizzBuzzSum.csv";

    private static final Set<String> TOO_BOUNDED = Set.of(new String[]{
        "pin2t(0, 100000, 20)",
        "rombell(0, 1000, 20)",
        "rombell(0, 100000, 20)",
    });

    @ParameterizedTest(name = "[{index}] Run {0} variant with range [{1} .. {2}] and limit {3}")
    @ImplementationSource(
        implementation = FizzBuzzSum.Implementation.class,
        method = @MethodSource("test1")
    )
    void test1(Implementation<FizzBuzzSum> variant, int min, int max, int lim, String expected) throws IOException {
        String name = String.format("%s(%d, %d, %d)", variant.name(), min, max, lim);
        try (var stream = new ByteArrayOutputStream()) {
            var out = new PrintStream(stream, false, UTF_8);
            assertThatCode(() -> variant.get().calculate(out, min, max, lim)).doesNotThrowAnyException();
            out.flush();
            if (TOO_BOUNDED.contains(name)) {
                assertThat(stream.toString(UTF_8)).as(name).isNotEqualToNormalizingWhitespace(expected);
            } else {
                assertThat(stream.toString(UTF_8)).as(name).isEqualToNormalizingWhitespace(expected);
            }
        }
    }

    private static Stream<Arguments> test1() {
        //expected too long for @CsvFileSource which allow only 4097
        return Optional.ofNullable(FizzBuzzSumTest.class.getResource(SOURCE))
            .flatMap(it -> Try.call(it::toURI).toOptional())
            .map(Path::of)
            .filter(Files::exists)
            .flatMap(it -> Try.call(() -> Files.lines(it, UTF_8)).toOptional())
            .stream().flatMap(identity())
            .skip(1)
            .map(it -> it.split("\\|"))
            .map(it -> new Object[]{parseInt(it[0]), parseInt(it[1]), parseInt(it[2]), it[3].replace(",", lineSeparator())})
            .map(Arguments::of);
    }

}
