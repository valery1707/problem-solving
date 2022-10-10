package name.valery1707.problem.e.olymp

/**
 * # Сумма всех
 *
 * Вычислите сумму всех заданных чисел.
 *
 * ## Входные данные
 *
 * Содержит `n` (`1 ≤ n ≤ 10^5`) целых чисел.
 * Все числа не превосходят `10^9` по абсолютной величине.
 *
 * ## Выходные данные
 *
 * Выведите сумму всех заданных чисел.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/520">Сумма всех</a>
 */
interface Problem520K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem520K {
        sequence {
            override fun main(args: Array<String>) {
                generateSequence(::readlnOrNull)
                    .flatMap { it.splitToSequence(' ') }
                    .filter(String::isNotBlank)
                    .map(String::toLong)
                    .reduce(Long::plus)
                    .also(::println)
            }
        },
    }
}
