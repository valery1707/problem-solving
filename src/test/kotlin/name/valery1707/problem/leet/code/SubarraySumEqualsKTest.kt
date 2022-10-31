package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class SubarraySumEqualsKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [SubarraySumEqualsKJ.Implementation::class],
        csv = [CsvSource(
            "'[1,1,1]', 2, 2",//`[1,1`, `1,1]`
            "'[1,2,3]', 3, 2",//`[1,2`, `3]`
            "'[1,2,0,3]', 3, 4",//`[1,2`, `[1,2,0`, `0,3]`, `3]`
            "'[0,1,2,0,3]', 3, 6",//`[0,1,2`, `[0,1,2,0`, `1,2`, `1,2,0`, `0,3]`, `3]`
            "'[0,1,2,0,3,0]', 3, 8",//`[0,1,2`, `[0,1,2,0`, `1,2`, `1,2,0`, `0,3`, `0,3,0]`, `3`, `3,0]`
            "'[1,2,-1,0,1,3]', 3, 4",//`[1,2`, `[1,2,-1,0,1`, `-1,0,1,3]`, `3]`
            "'[1,2,3,-3]', 3, 3",//`[1,2`, `[1,2,3,-3]`, `3`
        )]
    )
    fun test1(variant: Implementation<SubarraySumEqualsKJ>, numsRaw: String, k: Int, expected: Int) {
        val nums = numsRaw.trim('[', ']').split(',').map(String::toInt).toIntArray()
        assertThat(variant.get().subarraySum(nums, k)).isEqualTo(expected)
    }
}
