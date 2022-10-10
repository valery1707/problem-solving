package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem520KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem520K.Implementation::class],
        csv = [CsvSource(
            "2 3|   1      1,7",
        )]
    )
    internal fun test1(variant: Implementation<Problem520K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
