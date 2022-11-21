package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8687KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8687K.Implementation::class],
        csv = [CsvSource(
            "6.23 5.123 7.32141|2.12 4 3.012|3 3 3.033,18.6744|9.1320|9.0330",
            "0 0 0,0.0000",
            "0 0 0||1 1 1,0.0000|3.0000",
            "'',''",
            "1,1.0000",
            //Не гарантирую что при проверке идёт действительно такой текст, но что-то явно ломало парсинг числа
            "1 2 3 trash,6.0000",
        )]
    )
    internal fun test1(variant: Implementation<Problem8687K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
