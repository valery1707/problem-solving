package name.valery1707.problem.e.olymp

/**
 * # Число в обратном порядке
 *
 * Запишите целое неотрицательное число `n` в обратном порядке.
 *
 * ## Входные данные
 *
 * Одно целое неотрицательное **64-х** разрядное число.
 *
 * ## Выходные данные
 *
 * Выведите число в обратном порядке.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/1607">Число в обратном порядке</a>
 */
interface Problem1607K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1607K {
        simple {
            override fun main(args: Array<String>) {
                val src = readln()
                val dst = src.reversed()
                println(dst)
            }
        },
    }
}
