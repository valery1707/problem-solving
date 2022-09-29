package name.valery1707.problem.habr;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FizzBuzzSumTest {

    private static final Set<String> TOO_BOUNDED = Set.of(new String[]{
        "pin2t(0, 100000, 20)",
        "rombell(0, 1000, 20)",
        "rombell(0, 100000, 20)",
    });

    @ParameterizedTest(name = "[{index}] Run {0} variant with range [{1} .. {2}] and limit {3}")
    @ImplementationSource(
        implementation = FizzBuzzSum.Implementation.class,
        csvFiles = @CsvFileSource(delimiter = '|', numLinesToSkip = 1, maxCharsPerColumn = 50_000, resources = "/habr/fizzBuzzSum.csv")
    )
    void test1(Implementation<FizzBuzzSum> variant, int min, int max, int lim, String expected) throws IOException {
        String name = String.format("%s(%d, %d, %d)", variant.name(), min, max, lim);
        expected = expected.replace(",", lineSeparator());
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

}
