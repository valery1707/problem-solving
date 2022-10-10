package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Количество максимальных цифр
 *
 * Найдите, сколько раз встречается максимальная цифра в натуральном числе `n`.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` `(n < 10^18)`.
 *
 * ## Выходные данные
 *
 * Выведите, сколько раз встречается максимальная цифра в натуральном числе `n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8631">Количество максимальных цифр</a>
 */
interface Problem8631K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8631K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val max = n.groupBy { it }
                    .maxByOrNull { it.key }
                    ?.value?.size
                println(max)
            }
        },
        numberProcessing {
            override fun main(args: Array<String>) {
                var n = readln().toLong().absoluteValue
                var max = 0
                var count = if (n == 0L) 1 else 0
                while (n > 0) {
                    val m = (n % 10).toInt()
                    if (max < m) {
                        max = m
                        count = 1
                    } else if (max == m) {
                        count++
                    }
                    n /= 10
                }
                println(count)
            }
        },
    }
}
