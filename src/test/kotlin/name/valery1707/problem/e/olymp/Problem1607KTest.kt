package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem1607KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem1607K.Implementation::class],
        csv = [CsvSource(
            "1234,4321",
            "100,001",
        )]
    )
    internal fun test1(variant: Implementation<Problem1607K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
