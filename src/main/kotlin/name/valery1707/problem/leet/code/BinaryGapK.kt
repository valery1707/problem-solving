package name.valery1707.problem.leet.code

import kotlin.math.max

/**
 * # 868. Binary Gap
 *
 * Given a positive integer `n`, find and return the **longest distance** between any two **adjacent** `1`'s in the *binary representation* of `n`.
 * If there are no two adjacent `1`'s, return `0`.
 *
 * Two `1`'s are **adjacent** if there are only `0`'s separating them (possibly no `0`'s).
 * The **distance** between two `1`'s is the absolute difference between their bit positions.
 * For example, the two `1`'s in "1001" have a distance of `3`.
 *
 * ### Constraints
 * * `1 <= n <= 10^9`
 *
 * @see <a href="https://leetcode.com/problems/binary-gap/">868. Binary Gap</a>
 */
interface BinaryGapK {
    fun binaryGap(n: Int): Int

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : BinaryGapK {
        naive {
            override fun binaryGap(n: Int): Int {
                var max = 0
                var mask = 1
                var curr = 0
                var prev = -1
                while (mask < n) {
                    if (n.and(mask) == mask) {
                        if (prev in 0 until curr) {
                            max = max(curr - prev, max)
                        }
                        prev = curr
                    }
                    mask = mask shl 1
                    curr++
                }
                return max
            }
        },
    }
}
