package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Максимальная цифра числа
 *
 * Найдите максимальную цифру в натуральном числе `n`.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` `(n < 10^18)`.
 *
 * ## Выходные данные
 *
 * Выведите максимальную цифру в числе `n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8630">Максимальная цифра числа</a>
 */
interface Problem8630K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8630K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val max = n.maxOrNull()
                println(max)
            }
        },
        numberProcessing {
            override fun main(args: Array<String>) {
                var n = readln().toLong().absoluteValue
                var max = 0
                while (n > 0) {
                    val m = (n % 10).toInt()
                    if (max < m) {
                        max = m
                    }
                    n /= 10
                }
                println(max)
            }
        },
    }
}
