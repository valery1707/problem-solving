package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8682KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8682K.Implementation::class],
        csv = [CsvSource(
            "0,0",
            "1,1",
            "2,0",
            "3,3",
            "1234,13",
            "2468,0",
            "123456789,13579",
            "-1234,-13",
            "-13,-13",
            "-24,0",
            "99999999999999999,99999999999999999",
        )]
    )
    internal fun test1(variant: Implementation<Problem8682K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
