package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource

internal class ContinuousSubarraySumJTest {
    /**
     * Указанные варианты на указанных (и выше) значениях работают слишком медленно - выключаем их из тестов.
     */
    private val speedBarrier = mapOf<ContinuousSubarraySumJ, Int>(
        ContinuousSubarraySumJ.Implementation.naive to 100,
    )

    @ParameterizedTest(name = "[{index}] {0}([...], {2}, {3})")
    @ImplementationSource(
        implementation = [ContinuousSubarraySumJ.Implementation::class],
        csv = [CsvSource(
            //From description
            "'[23,2,4,6,7]', 6, true",
            "'[23,2,6,4,7]', 6, true",
            "'[23,2,6,4,7]', 13, false",
            //Self-invented
            "'[1,5,1]', 5, false",
            //From submission
            "'[23,2,4,6,6]', 7, true",
        )],
        csvFiles = [CsvFileSource(resources = ["/leet/code/ContinuousSubarraySum.csv"], maxCharsPerColumn = 600_000)],
    )
    internal fun test1(variant: Implementation<ContinuousSubarraySumJ>, numsRaw: String, k: Int, expected: Boolean) {
        speedBarrier[variant.get()]?.also {
            assumeThat(numsRaw).hasSizeLessThanOrEqualTo(it)
        }
        val nums = numsRaw.trim('[', ']').split(',').map(String::toInt).toIntArray()
        assertThat(variant.get().checkSubarraySum(nums, k)).isEqualTo(expected)
    }
}
