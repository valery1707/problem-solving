package name.valery1707.problem.e.olymp

import kotlin.math.absoluteValue

/**
 * # Сумма цифр числа
 *
 * Найти сумму цифр целого числа.
 *
 * ## Входные данные
 *
 * Одно целое **32-х** разрядное число `n` (число может быть отрицательным).
 *
 * ## Выходные данные
 *
 * Вывести сумму цифр числа `n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/1603">Сумма цифр числа</a>
 */
interface Problem1603K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1603K {
        simple {
            override fun main(args: Array<String>) {
                var src = readln().toInt().absoluteValue
                var sum = 0
                while (src != 0) {
                    sum += src % 10
                    src /= 10
                }
                println(sum)
            }
        },
    }
}
