package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8631KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8631K.Implementation::class],
        csv = [CsvSource(
            "2354,1",
            "609432232423423,1",
            "12345,1",
            "54321,1",
            "-17,1",
            "123456789012345678,1",
            "1707,2",
        )]
    )
    internal fun test1(variant: Implementation<Problem8631K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
