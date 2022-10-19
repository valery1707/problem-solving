package name.valery1707.problem

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class StringConcatenationKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [StringConcatenationK.Implementation::class],
        csv = [CsvSource(
            "lhs,rhs,lhs_rhs",
        )]
    )
    internal fun testImplementation(variant: Implementation<StringConcatenationK>, lhs: String, rhs: String, expected: String) {
        assertThat(variant.get().accept(lhs, rhs)).isEqualTo(expected)
    }
}
