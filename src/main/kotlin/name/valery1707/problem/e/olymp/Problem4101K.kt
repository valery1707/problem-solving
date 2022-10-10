package name.valery1707.problem.e.olymp

/**
 * # Трехзначные числа
 *
 * Найти все трехзначные числа, сумма цифр которых равна `n`.
 *
 * ## Входные данные
 *
 * Одно число `n` (`0 ≤ n ≤ 28`).
 *
 * ## Выходные данные
 *
 * В первой строке выведите количество найденных трехзначных чисел.
 * В следующих строках выведите сами трехзначные числа в порядке возрастания.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/4101">Трехзначные числа</a>
 */
interface Problem4101K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem4101K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                val numbers = (100..999)
                    .map(Int::toString)
                    .filter { it.chars().map { d -> d.toChar().digitToInt() }.sum() == n }
                println(numbers.size)
                numbers.forEach(::println)
            }
        },
    }
}
