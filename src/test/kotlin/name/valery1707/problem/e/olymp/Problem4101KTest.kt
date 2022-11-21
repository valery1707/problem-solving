package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem4101KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem4101K.Implementation::class],
        csv = [CsvSource(
            "3,6|" +
                "102|" +
                "111|" +
                "120|" +
                "201|" +
                "210|" +
                "300",
        )]
    )
    internal fun test1(variant: Implementation<Problem4101K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
