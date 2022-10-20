package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ConcatenationOfConsecutiveBinaryNumbersKTest {
    /**
     * Указанные варианты на указанных (и выше) значениях работают слишком медленно - выключаем их из тестов.
     */
    private val speedBarrier = mapOf<ConcatenationOfConsecutiveBinaryNumbersK, Int>(
        ConcatenationOfConsecutiveBinaryNumbersK.Implementation.naive to 60_000,
        ConcatenationOfConsecutiveBinaryNumbersK.Implementation.sumThenModulo_fold to 60_000,
        ConcatenationOfConsecutiveBinaryNumbersK.Implementation.sumThenModulo_for to 60_000,
    )

    @ParameterizedTest
    @ImplementationSource(
        implementation = [ConcatenationOfConsecutiveBinaryNumbersK.Implementation::class],
        csv = [CsvSource(
            "1,1",
            "3,27",
            "12,505379714",
            "63566,57361070",
            "100000,757631812",
        )]
    )
    internal fun test1(variant: Implementation<ConcatenationOfConsecutiveBinaryNumbersK>, n: Int, expected: Int) {
        assumeThat(speedBarrier).noneSatisfy { type, barrier ->
            assertThat(type).isEqualTo(variant.get())
            assertThat(barrier).isLessThanOrEqualTo(n)
        }
        assertThat(variant.get().concatenatedBinary(n)).isEqualTo(expected)
    }
}
