package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8533KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8533K.Implementation::class],
        csv = [CsvSource(
            "2000 2015,2013 2014 2015",
            "9875 9999,9875 9876",
        )]
    )
    internal fun test1(variant: Implementation<Problem8533K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
