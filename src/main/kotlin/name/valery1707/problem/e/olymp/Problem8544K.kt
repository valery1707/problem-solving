package name.valery1707.problem.e.olymp

import kotlin.math.pow

/**
 * # Квадраты чисел
 *
 * Выведите квадраты всех натуральных чисел не больших `n` в возрастающем порядке.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`n ≤ 10^9`).
 *
 * ## Выходные данные
 *
 * Выведите список квадратов всех натуральных чисел не больших `n` в возрастающем порядке.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8544">Квадраты чисел<a>
 */
interface Problem8544K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8544K {
        doWhile {
            override fun main(args: Array<String>) {
                val scan = java.util.Scanner(System.`in`)
                val src = scan.nextInt()
                if (src < 1) {
                    return
                }
                var i = 1
                do {
                    val needSpace = i > 1
                    val pow = (i++).toDouble().pow(2).toInt()
                    if (pow <= src) {
                        if (needSpace) {
                            print(' ')
                        }
                        print(pow)
                    }
                } while (pow < src)
                println()
            }
        },

        /**
         * Не прошёл по скорости.
         */
        sequence {
            override fun main(args: Array<String>) {
                val scan = java.util.Scanner(System.`in`)
                val src = scan.nextInt()
                (1..src).asSequence()
                    .map { it.toDouble().pow(2).toInt() }
                    .filter { it <= src }
                    .joinToString(separator = " ")
                    .also { println(it) }
            }
        },
    }
}
