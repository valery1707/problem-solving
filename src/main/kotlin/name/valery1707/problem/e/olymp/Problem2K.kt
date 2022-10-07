package name.valery1707.problem.e.olymp

/**
 * # Цифры
 *
 * Вычислить количество цифр целого неотрицательного числа `n`.
 *
 * ## Входные данные
 *
 * Одно целое неотрицательное число `n (0 <= n <= 2*10^9)`.
 *
 * ## Выходные данные
 *
 * Выведите количество цифр в числе `n`.
 */
interface Problem2K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem2K {
        simple {
            override fun main(args: Array<String>) {
                val len = readln().trimStart('0').length
                println(len.coerceAtLeast(1))
            }
        },
    }
}
