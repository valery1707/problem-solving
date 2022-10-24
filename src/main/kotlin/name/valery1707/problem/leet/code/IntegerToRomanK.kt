package name.valery1707.problem.leet.code

/**
 * # 12. Integer to Roman
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
 * Given an integer, convert it to a roman numeral.
 *
 * ### Constraints
 * * `1 <= num <= 3999`
 *
 * <a href="https://leetcode.com/problems/integer-to-roman/">12. Integer to Roman</a>
 */
interface IntegerToRomanK {
    fun intToRoman(num: Int): String

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : IntegerToRomanK {
        simple {
            //Should be static
            private val mapper = sortedMapOf(
                Comparator.reverseOrder(),
                1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C", 500 to "D", 1000 to "M",
                4 to "IV", 9 to "IX", 40 to "XL", 90 to "XC", 400 to "CD", 900 to "CM",
            )

            override fun intToRoman(num: Int): String {
                var v = num
                val s = StringBuilder()
                for ((int, roman) in mapper) {
                    while (v >= int) {
                        s.append(roman)
                        v -= int
                    }
                }
                return s.toString()

            }
        },
        optimized {
            //Should be static
            private val mapper = sortedMapOf(
                Comparator.reverseOrder(),
                1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C", 500 to "D", 1000 to "M",
                4 to "IV", 9 to "IX", 40 to "XL", 90 to "XC", 400 to "CD", 900 to "CM",
            )

            override fun intToRoman(num: Int): String {
                var v = num
                val s = StringBuilder()
                for ((int, roman) in mapper) {
                    if (v >= int) {
                        s.append(roman.repeat(v / int))
                        v %= int
                    }
                }
                return s.toString()
            }
        },
        arrays {
            //Should be static
            private val integers = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
            private val romans = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
            override fun intToRoman(num: Int): String {
                var v = num
                var i = 0
                val s = StringBuilder()
                while (i < integers.size && v > 0) {
                    val int = integers[i]
                    if (v >= int) {
                        s.append(romans[i].repeat(v / int))
                        v %= int
                    }
                    i++
                }
                return s.toString()
            }
        },
    }
}
