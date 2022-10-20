package name.valery1707.problem.leet.code

import java.math.BigInteger

/**
 * # 1680. Concatenation of Consecutive Binary Numbers
 *
 * Given an integer `n`, return the **decimal value** of the binary string formed by concatenating the binary representations of `1` to `n` in order,
 * **modulo** `10^9 + 7`.
 *
 * ### Constraints
 * * `1 <= n <= 10^5`
 *
 * @see <a href="https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/">1680. Concatenation of Consecutive Binary Numbers</a>
 */
interface ConcatenationOfConsecutiveBinaryNumbersK {
    fun concatenatedBinary(n: Int): Int

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : ConcatenationOfConsecutiveBinaryNumbersK {
        naive {
            override fun concatenatedBinary(n: Int): Int {
                val modulo = (1e9 + 7).toLong().toBigInteger()
                return (1..n)
                    .joinToString(separator = "") { it.toString(2) }
                    .toBigInteger(2)
                    .mod(modulo)
                    .intValueExact()
            }
        },
        sumThenModulo_fold {
            override fun concatenatedBinary(n: Int): Int {
                val modulo = (1e9 + 7).toLong().toBigInteger()
                return n.downTo(1)
                    .fold(Pair(BigInteger.ZERO, 0)) { sumAndShift, item ->
                        val shift = sumAndShift.second
                        val sum = item.toBigInteger().shl(shift).plus(sumAndShift.first)
                        Pair(sum, shift + item.toString(2).length)
                    }
                    .first
                    .mod(modulo)
                    .intValueExact()
            }
        },
        sumThenModulo_for {
            override fun concatenatedBinary(n: Int): Int {
                val modulo = (1e9 + 7).toLong().toBigInteger()
                var sum = BigInteger.ZERO
                var shift = 0
                for (item in n.downTo(1)) {
                    sum = item.toBigInteger().shl(shift).plus(sum)
                    shift += item.toString(2).length
                }
                return sum.mod(modulo).intValueExact()
            }
        },

        /**
         * @see <a href="https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/discuss/2612207/Java-oror-Explained-in-Detailoror-Fast-O(N)-Solutionoror-Bit-Manipulation-and-Math">Java || 👍Explained in Detail👍 || Fast O(N) Solution✅ || Bit Manipulation & Math</a>
         */
        cheehwatang {
            override fun concatenatedBinary(n: Int): Int {
                val modulo = (1e9 + 7).toLong()
                return (1..n)
                    .fold(Pair(0L, 0)) { sumAndShift, item ->
                        // Если мы на текущем элементе перешли на новую степень двойки
                        val shift = if (item.and(item - 1) == 0) sumAndShift.second + 1 else sumAndShift.second
                        // Предыдущую накопленную сумму смещаем на количество бит у текущего числа и добавляем к нему текущее число
                        val sum = sumAndShift.first.shl(shift) + item
                        // Чтобы не выскочить за границу Int нужно ограничить сумму по указанному модулю
                        Pair(sum % modulo, shift)
                    }
                    .first
                    .toInt()
            }
        },
    }
}
