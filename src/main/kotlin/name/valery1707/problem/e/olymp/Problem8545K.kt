package name.valery1707.problem.e.olymp

/**
 * # Таблица умножения
 *
 * Выведите таблицу умножения `n * n` с выравниванием.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`1 ≤ n ≤ 9`).
 *
 * ## Выходные данные
 *
 * Выведите таблицу умножения `n * n` с выравниванием как показано в примере.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8545">Таблица умножения</a>
 */
interface Problem8545K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8545K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                (1..n)
                    .map { x ->
                        (1..n)
                            .map { y -> x * y }
                            .joinToString(separator = " ", postfix = " ") { "%2d".format(it) }
                    }
                    .forEach { println(it) }
            }
        },
    }
}
