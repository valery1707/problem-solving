package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8897KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8897K.Implementation::class],
        csv = [CsvSource(
            "0,10",
            "3,10",
            "7,10",
            "17,20",
            "10,20",
            "-3,0",
            "-5,0",
            "-7,0",
            "-10,0",
            "-15,-10",
            "-2147483648,-2147483640",
        )]
    )
    internal fun test1(variant: Implementation<Problem8897K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
