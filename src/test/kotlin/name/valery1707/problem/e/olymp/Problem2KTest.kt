package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem2KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem2K.Implementation::class],
        csv = [CsvSource(
            "0,1",
            "1,1",
            "10,2",
            "17,2",
            "999,3",
            "12343,5",
            "0017,2",
        )]
    )
    internal fun test1(variant: Implementation<Problem2K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
