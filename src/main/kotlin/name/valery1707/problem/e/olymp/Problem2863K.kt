package name.valery1707.problem.e.olymp

/**
 * # Нечетные делители
 *
 * Выведите все нечетные делители натурального числа `n`.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`n ≤ 100000`).
 *
 * ## Выходные данные
 *
 * Вывести все нечетные делители числа `n`.
 * Каждый делитель выводить в отдельной строке.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/2863">Нечетные делители</a>
 */
interface Problem2863K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem2863K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                println(1)
                (3..n / 2)
                    .step(2)
                    .asSequence()
                    .filter { n % it == 0 }
                    .forEach(::println)
                if (n > 1 && n % 2 != 0) {
                    println(n)
                }
            }
        },
    }
}
