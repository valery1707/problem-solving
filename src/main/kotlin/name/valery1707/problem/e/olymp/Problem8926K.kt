package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Замена четности
 *
 * Задано натуральное число `n`.
 * Увеличьте на `1` все его четные цифры и уменьшите на `1` все его нечетные цифры.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n`.
 *
 * ## Выходные данные
 *
 * Вывести обновленное число.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8926">Замена четности</a>
 */
interface Problem8926K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8926K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val res = n.map {
                    if (it == '-') {
                        it
                    } else {
                        val s = it.digitToInt()
                        val d = if (s % 2 == 0) s + 1 else s - 1
                        d.digitToChar()
                    }
                }
                    .joinToString(separator = "")
                    .trimStart('0')
                    .ifEmpty { "0" }
                println(res)
            }
        },
        numberProcessing {
            override fun main(args: Array<String>) {
                val n = readln().toLong()
                var res = if (n != 0L) 0L else 1L
                var pow = 1L
                var v = n.absoluteValue
                while (v > 0) {
                    val s = (v % 10).toInt()
                    val d = if (s % 2 == 0) s + 1 else s - 1
                    res += d * pow
                    pow *= 10
                    v /= 10
                }
                res = if (n >= 0) res else -res
                println(res)
            }
        },
    }
}
