package name.valery1707.problem.leet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static name.valery1707.problem.TestUtilsJ.findVariants;
import static org.assertj.core.api.Assertions.assertThat;

class ReverseIntegerJTest {

    private static final Map<String, ReverseIntegerJ> VARIANTS = findVariants(ReverseIntegerJ.class);

    @ParameterizedTest
    @CsvSource({
        "0,0",
        "1,1",
        "12,21",
        "120,21",
        "123,321",
        "1234,4321",
        "-1,-1",
        "-12,-21",
        "-123,-321",
        "-1234,-4321",
        "1534236469,0",
    })
    void test1(int value, int expected) {
        assertThat(VARIANTS).allSatisfy((name, variant) ->
            assertThat(variant.reverse(value)).as(name).isEqualTo(expected)
        );
    }

    @Test
    void environment() {
        assertThat(VARIANTS).isNotEmpty();
    }

}
