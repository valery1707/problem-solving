package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8545KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8545K.Implementation::class],
        csv = [CsvSource(
            //Можно было ожидать и такого, но нет
//            "3,'" +
//                " 1 2 3 |" +
//                " 2 4 6 |" +
//                " 3 6 9 '",
            "3,'" +
                " 1  2  3 |" +
                " 2  4  6 |" +
                " 3  6  9 '",
            "5,'" +
                " 1  2  3  4  5 |" +
                " 2  4  6  8 10 |" +
                " 3  6  9 12 15 |" +
                " 4  8 12 16 20 |" +
                " 5 10 15 20 25 '",
            "7,'" +
                " 1  2  3  4  5  6  7 |" +
                " 2  4  6  8 10 12 14 |" +
                " 3  6  9 12 15 18 21 |" +
                " 4  8 12 16 20 24 28 |" +
                " 5 10 15 20 25 30 35 |" +
                " 6 12 18 24 30 36 42 |" +
                " 7 14 21 28 35 42 49 '",
        )]
    )
    internal fun test1(variant: Implementation<Problem8545K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
