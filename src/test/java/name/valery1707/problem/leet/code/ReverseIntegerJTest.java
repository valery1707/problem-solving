package name.valery1707.problem.leet.code;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ReverseIntegerJTest {

    @ParameterizedTest(name = "[{index}] {0}({1}) == {2}")
    @ImplementationSource(
        implementation = ReverseIntegerJ.Implementation.class,
        csv = @CsvSource({
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
    )
    void test1(Implementation<ReverseIntegerJ> variant, int value, int expected) {
        assertThat(variant.get().reverse(value)).isEqualTo(expected);
    }

}
