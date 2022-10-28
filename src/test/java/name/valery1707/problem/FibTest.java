package name.valery1707.problem;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FibTest {

    @ParameterizedTest
    @ImplementationSource(
        implementation = Fib.Implementation.class,
        csv = @CsvSource({
            "0,0",
            "1,1",
            "2,1",
            "3,2",
            "4,3",
            "5,5",
            "6,8",
            "7,13",
            "8,21",
            "9,34",
        })
    )
    void test1(Implementation<Fib> variant, int n, long expected) {
        assertThat(variant.get().fib(n)).isEqualTo(expected);
    }

}
