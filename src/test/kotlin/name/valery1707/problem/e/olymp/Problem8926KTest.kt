package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8926KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8926K.Implementation::class],
        csv = [CsvSource(
            "0,1",
            "1,0",
            "30,21",
            "21,30",
            "-21,-30",
            "1234567890,325476981",
        )]
    )
    internal fun test1(variant: Implementation<Problem8926K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
