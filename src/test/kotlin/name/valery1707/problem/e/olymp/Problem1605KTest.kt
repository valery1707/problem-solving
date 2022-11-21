package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem1605KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem1605K.Implementation::class],
        csv = [CsvSource(
            "43568,3",
            "1234567890123,2",
            "-7654321,6",
        )]
    )
    internal fun test1(variant: Implementation<Problem1605K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
