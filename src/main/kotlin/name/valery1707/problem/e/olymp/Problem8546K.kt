package name.valery1707.problem.e.olymp

/**
 * # Найдите сумму
 *
 * По заданному натуральному числу `n` вычислите сумму.
 * ```
 * S = 1/(1*2) + 1/(2*3) + /* ... */ + 1/(n * (n+1))
 * ```
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`n ≤ 1000`).
 *
 * ## Выходные данные
 *
 * Выведите сумму с **6** десятичными знаками.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8546">Найдите сумму</a>
 */
interface Problem8546K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8546K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                val sum = (1..n).map { it * (it + 1) }.sumOf { 1.0 / it }
                System.out.printf("%.06f%n", sum)
            }
        },
    }
}
