package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8917KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8917K.Implementation::class],
        csv = [CsvSource(
            "7,2 4",
            "0,''",
            "1,''",
            "2,''",
            "3,2",
            "16,2 4 8",
            "17,2 4 8 16",
        )]
    )
    internal fun test1(variant: Implementation<Problem8917K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
