package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class BinaryGapKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [BinaryGapK.Implementation::class],
        csv = [CsvSource(
            "22,2",//10110
            "8,0",//1000
            "5,2",//101

            "1000000000,3",//111011100110101100101000000000
        )]
    )
    internal fun test1(variant: Implementation<BinaryGapK>, n: Int, expected: Int) {
        assertThat(variant.get().binaryGap(n)).isEqualTo(expected)
    }
}
