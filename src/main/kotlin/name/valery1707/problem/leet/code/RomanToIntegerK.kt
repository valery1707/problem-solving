package name.valery1707.problem.leet.code

/**
 * # 13. Roman to Integer
 *
 * Roman numerals are represented by seven different symbols: `I`, `V`, `X`, `L`, `C`, `D` and `M`.
 *
 * | Symbol | Value |
 * |--------|-------|
 * | `I` | `1` |
 * | `V` | `5` |
 * | `X` | `10` |
 * | `L` | `50` |
 * | `C` | `100` |
 * | `D` | `500` |
 * | `M` | `1000` |
 *
 * For example, `2` is written as `II` in Roman numeral, just two ones added together.
 * `12` is written as `XII`, which is simply `X + II`.
 * The number `27` is written as `XXVII`, which is `XX + V + II`.
 *
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not `IIII`.
 * Instead, the number four is written as `IV`.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as `IX`.
 * There are six instances where subtraction is used:
 * * `I` can be placed before `V` (`5`) and `X` (`10`) to make `4` and `9`.
 * * `X` can be placed before `L` (`50`) and `C` (`100`) to make `40` and `90`.
 * * `C` can be placed before `D` (`500`) and `M` (`1000`) to make `400` and `900`.
 *
 * Given a roman numeral, convert it to an integer.
 *
 * ### Constraints
 * * `1 <= s.length <= 15`
 * * `s` contains only the characters (`'I', 'V', 'X', 'L', 'C', 'D', 'M'`)
 * * It is **guaranteed** that `s` is a valid roman numeral in the range `[1, 3999]`
 *
 * <a href="https://leetcode.com/problems/roman-to-integer/">13. Roman to Integer</a>
 */
interface RomanToIntegerK {
    fun romanToInt(s: String): Int

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : RomanToIntegerK {
        /**
         * Parse only valid values.
         */
        maps {
            //Should be static
            private val values = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
            private val prefix = mapOf('I' to setOf('V', 'X'), 'X' to setOf('L', 'C'), 'C' to setOf('D', 'M'))
            override fun romanToInt(s: String): Int {
                val max = s.length - 1
                var sum = 0;
                var i = 0;
                while (i <= max) {
                    val c = s[i]
                    sum += if (c in prefix && i < max && s[i + 1] in prefix[c]!!) {
                        values[s[++i]]!! - values[c]!!
                    } else {
                        values[c]!!
                    }
                    i++
                }
                return sum
            }
        },

        /**
         * Logic:
         * * Parse valid `IV`:
         *     * `prev == 0 && curr == 5 => sum = 0 + 5`
         *     * `prev == 5 && curr == 1 => sum = 5 - 1`
         *     * `sum == 4`
         * * Parse also invalid `IM`:
         *     * `prev == 0 && curr == 1000 => sum = 0 + 1000`
         *     * `prev == 1000 && curr == 1 => sum = 1000 - 1`
         *     * `sum == 999`
         */
        switch {
            override fun romanToInt(s: String): Int {
                var sum = 0
                var prev = 0
                for (i in (s.length - 1).downTo(0)) {
                    val curr = when (s[i]) {
                        'I' -> 1
                        'V' -> 5
                        'X' -> 10
                        'L' -> 50
                        'C' -> 100
                        'D' -> 500
                        'M' -> 1000
                        else -> 0
                    }
                    sum += if (curr >= prev) curr else -curr
                    prev = curr
                }
                return sum
            }
        },
    }
}
