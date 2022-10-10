package name.valery1707.problem.e.olymp

/**
 * # Степени двойки
 *
 * Для заданного натурального числа `n` вывести все степени двойки меньше `n` в порядке возрастания.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`n ≤ 10^9`).
 *
 * ## Выходные данные
 *
 * Выведите все степени двойки меньше `n` в порядке возрастания.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8917">Степени двойки</a>
 */
interface Problem8917K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8917K {
        /**
         * CPU: `78 ms`, RAM: `31.28 MiB`
         */
        `while` {
            override fun main(args: Array<String>) {
                val src = readln().toInt()
                if (src <= 2) {
                    return
                }
                var pow = 2
                while (pow < src) {
                    if (pow > 2) {
                        print(' ')
                    }
                    print(pow)
                    pow *= 2
                }
                println()
            }
        },

        /**
         * CPU: `88 ms`, RAM: `33.18 MiB`
         */
        sequence {
            override fun main(args: Array<String>) {
                val src = readln().toInt()
                generateSequence(2) { it * 2 }
                    .takeWhile { it < src }
                    .joinToString(separator = " ")
                    .also { println(it) }
            }
        },
    }
}
