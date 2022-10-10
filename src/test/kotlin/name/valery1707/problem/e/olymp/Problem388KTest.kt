package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem388KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem388K.Implementation::class],
        csv = [CsvSource(
            "1,0",
            "2,1",
            "3,3",
            "4,2",
            "11,6",
        )]
    )
    internal fun test1(variant: Implementation<Problem388K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
