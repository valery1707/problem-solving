package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Произведение ненулевых цифр
 *
 * Найдите произведение ненулевых цифр числа.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` `(n < 10^9)`.
 *
 * ## Выходные данные
 *
 * Вывести произведение ненулевых цифр числа `n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8681">Произведение ненулевых цифр</a>
 */
interface Problem8681K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8681K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val mult = n.trimStart('-')
                    .map { it.digitToInt() }
                    .filter { it != 0 }
                    .reduceOrNull(Int::times)
                    ?: 1
                println(mult)
            }
        },
        numberProcessing {
            override fun main(args: Array<String>) {
                var n = readln().toLong().absoluteValue
                var mult = 1
                while (n > 0) {
                    val m = (n % 10).toInt()
                    if (m > 0) {
                        mult *= m
                    }
                    n /= 10
                }
                println(mult)
            }
        },
    }
}
