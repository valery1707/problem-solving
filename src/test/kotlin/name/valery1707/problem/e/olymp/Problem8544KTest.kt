package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8544KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8544K.Implementation::class],
        csv = [CsvSource(
            "0,''",
            "1,1",
            "10,1 4 9",
            "16,1 4 9 16",
            "20,1 4 9 16",
        )]
    )
    internal fun test1(variant: Implementation<Problem8544K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
