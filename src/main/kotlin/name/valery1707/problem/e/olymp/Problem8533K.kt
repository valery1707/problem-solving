package name.valery1707.problem.e.olymp

/**
 * # Числа с разными цифрами
 *
 * Выведите все четырехзначные числа от `a` до `b`, содержащие разные цифры.
 *
 * ## Входные данные
 *
 * Два целых числа `a` и `b` (`1000 ≤ a ≤ b ≤ 9999`).
 *
 * ## Выходные данные
 *
 * Выведите в одной строке все числа от `a` до `b` с разными цифрами.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8533">Числа с разными цифрами</a>
 */
interface Problem8533K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8533K {
        range {
            override fun main(args: Array<String>) {
                val src = readln().split(' ').map(String::toInt)
                val res = (src[0]..src[1])
                    .map(Int::toString)
                    .filter { it.chars().distinct().count() == 4L }
                    .joinToString(separator = " ")
                println(res)
            }
        },
    }
}
