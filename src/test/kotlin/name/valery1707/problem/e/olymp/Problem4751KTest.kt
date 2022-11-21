package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem4751KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem4751K.Implementation::class],
        csv = [CsvSource(
            "1|451,451 451",
            "4|" +
                "134 475 30 424|" +
                "303 151 419 235|" +
                "248 166 90 42|" +
                "318 237 184 36," +
                "411 1327",
        )]
    )
    internal fun test1(variant: Implementation<Problem4751K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
