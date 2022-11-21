package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem1000KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem1000K.Implementation::class],
        csv = [CsvSource(
            "1 1,2",
            "1 1|1 2,2|3",
            "1 1|1 2|17 -7,2|3|10",
        )]
    )
    internal fun test1(variant: Implementation<Problem1000K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
