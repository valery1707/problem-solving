package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem1609KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem1609K.Implementation::class],
        csv = [CsvSource(
            "25557|5,3",
            "100|0,2",
            "0|1,0",
            "0|0,1",
            "-100|0,2",
        )]
    )
    internal fun test1(variant: Implementation<Problem1609K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
