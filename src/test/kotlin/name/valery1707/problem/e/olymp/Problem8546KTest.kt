package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8546KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8546K.Implementation::class],
        csv = [CsvSource(
            "5,0.833333",
            "12,0.923077",
        )]
    )
    internal fun test1(variant: Implementation<Problem8546K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
