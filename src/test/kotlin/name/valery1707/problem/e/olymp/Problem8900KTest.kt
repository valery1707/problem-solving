package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8900KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8900K.Implementation::class],
        csv = [CsvSource(
            "0,7",
            "3,7",
            "7,14",
            "17,21",
            "10,14",
            "-3,0",
            "-5,0",
            "-7,0",
            "-15,-14",
        )]
    )
    internal fun test1(variant: Implementation<Problem8900K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
