package name.valery1707.problem.leet.code

import kotlin.math.max
import kotlin.math.roundToInt

/**
 * # 605. Can Place Flowers
 *
 * You have a long flowerbed in which some of the plots are planted, and some are not.
 * However, flowers cannot be planted in **adjacent** plots.
 *
 * Given an integer array `flowerbed` containing `0`'s and `1`'s, where `0` means *empty* and `1` means not *empty*, and an integer `n`,
 * return *if* `n` new flowers can be planted in the `flowerbed` without violating the no-adjacent-flowers rule.
 *
 * ### Constraints
 * * `1 <= flowerbed.length <= 2 * 10^4`
 * * `flowerbed[i]` is `0` or `1`
 * * There are no two adjacent flowers in `flowerbed`
 * * `0 <= n <= flowerbed.length`
 *
 * @see <a href="https://leetcode.com/problems/can-place-flowers/">605. Can Place Flowers</a>
 */
interface CanPlaceFlowersK {
    fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : CanPlaceFlowersK {
        regexp {
            override fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
                //Цветов больше чем возможных мест в принципе
                if (n > (flowerbed.size / 2.0).roundToInt()) {
                    return false
                }
                //0 цветов всегда поместится
                if (n == 0) {
                    return true
                }

                val str = flowerbed
                    .joinToString(separator = "", prefix = "0", postfix = "0", transform = Int::toString)
                val count = "00+".toRegex()
                    .findAll(str)
                    .map { it.value.length }
                    //"0"->[1]->0
                    //"00"->[2]->0
                    //"000"->[3]->1
                    //"0000"->[4]->1
                    //"00000"->[5]->2
                    //"000000"->[6]->2
                    //"0000000"->[7]->3
                    .map { (it + 1) / 2 - 1 }
                    .filter { it > 0 }.sum()
                return n <= count
            }
        },
        indexes {
            override fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
                //Цветов больше чем возможных мест в принципе
                if (n > (flowerbed.size / 2.0).roundToInt()) {
                    return false
                }
                //0 цветов всегда поместится
                if (n == 0) {
                    return true
                }

                val count: (curr: Int, prev: Int) -> Int = { curr, prev ->
                    val count = curr - prev - 1
                    //1->0, 2->0, 3->1, 4->1, 5->2, 6->2
                    max(0, (count + 1) / 2 - 1)
                }
                var possible = 0
                var prevUsed = -2
                for (i in flowerbed.indices) {
                    if (flowerbed[i] == 1) {
                        possible += count(i, prevUsed)
                        prevUsed = i
                    }
                }
                possible += count(flowerbed.lastIndex + 2, prevUsed)
                return n <= possible
            }
        },
    }
}
