package name.valery1707.problem;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class StringConcatenationJTest {

    @ParameterizedTest
    @ImplementationSource(
        implementation = StringConcatenationJ.Implementation.class,
        csv = @CsvSource({
            "lhs,rhs,lhs_rhs",
        })
    )
    void testImplementation(Implementation<StringConcatenationJ> variant, String lhs, String rhs, String expected) {
        assertThat(variant.get().accept(lhs, rhs)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
        0,
        1,
        10,
        50,
    })
    void testGenerator(int len) {
        assertThat(StringConcatenationJ.generate(ThreadLocalRandom.current(), len)).hasSize(len);
    }

}
