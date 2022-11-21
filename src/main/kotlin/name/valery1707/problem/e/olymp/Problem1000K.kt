package name.valery1707.problem.e.olymp

/**
 * # Задача a + b
 *
 * Вычислите сумму `a` + `b`.
 *
 * ## Входные данные
 *
 * В каждой строке задано два целых числа `a` и `b` (`|a|, |b| ≤ 30000`).
 *
 * ## Выходные данные
 *
 * Для каждого теста выведите сумму `a + b` в отдельной строке.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/1000">Задача a + b</a>
 */
interface Problem1000K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1000K {
        sequence {
            override fun main(args: Array<String>) {
                generateSequence(::readlnOrNull)
                    .map { it.splitToSequence(' ').map(String::toInt).reduce(Int::plus) }
                    .forEach(::println)
            }
        },
    }
}
