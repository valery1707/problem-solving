package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Количество заданных цифр в числе
 *
 * Подсчитать количество цифр `a` в числе `n`.
 *
 * ## Входные данные
 *
 * В первой строке записано одно целое **32**-разрядное число `n`.
 * Число `n` может быть отрицательным.
 * Во второй строке задано цифру `а`.
 *
 * ## Выходные данные
 *
 * Вывести количество цифр `a` в числе `n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/1609">Количество заданных цифр в числе</a>
 */
interface Problem1609K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1609K {
        pureString {
            override fun main(args: Array<String>) {
                val n = readln()
                val a = readln()[0]
                val count = n.count(a::equals)
                println(count)
            }
        },
        intProcessing {
            override fun main(args: Array<String>) {
                var n = readln().toInt().absoluteValue
                val a = readln().toInt()
                var count = if (n == 0 && a == 0) 1 else 0
                while (n > 0) {
                    if (n % 10 == a) {
                        count++
                    }
                    n /= 10
                }
                println(count)
            }
        },
    }
}
