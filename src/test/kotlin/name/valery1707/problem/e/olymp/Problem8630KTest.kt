package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8630KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8630K.Implementation::class],
        csv = [CsvSource(
            "2354,5",
            "609432232423423,9",
            "12345,5",
            "54321,5",
            "-17,7",
            "123456789012345678,9",
        )]
    )
    internal fun test1(variant: Implementation<Problem8630K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
