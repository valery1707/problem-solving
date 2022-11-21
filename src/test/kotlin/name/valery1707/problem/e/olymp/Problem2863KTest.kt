package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem2863KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem2863K.Implementation::class],
        csv = [CsvSource(
            "1,1",
            "2,1",
            "3,1|3",
            "12,1|3",
        )]
    )
    internal fun test1(variant: Implementation<Problem2863K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
