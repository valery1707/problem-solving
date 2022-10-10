package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Удалить четные цифры
 *
 * Из заданного натурального числа удалить все четные цифры.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` `(n ≤ 10^18)`.
 *
 * ## Выходные данные
 *
 * Вывести число `n`, из которого удалены все четные цифры.
 * Если исходное число `n` содержит только четные цифры, то вывести `0`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8682">Удалить четные цифры</a>
 */
interface Problem8682K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8682K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val res = n
                    .filter { it == '-' || it.digitToInt() % 2 != 0 }
                    .let { if (it == "-" || it.isEmpty()) "0" else it }
                println(res)
            }
        },
        numberProcessing {
            override fun main(args: Array<String>) {
                val n = readln().toLong()
                var res = 0L
                var pow = 1L
                var v = n.absoluteValue
                while (v > 0) {
                    val i = (v % 10).toInt()
                    if (i % 2 != 0) {
                        res += i * pow
                        pow *= 10
                    }
                    v /= 10
                }
                res = if (n > 0) res else -res
                println(res)
            }
        },
    }
}
