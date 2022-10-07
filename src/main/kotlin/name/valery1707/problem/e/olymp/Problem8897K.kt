package name.valery1707.problem.e.olymp

import java.math.BigInteger

/**
 * # Следующее число 2
 *
 * Для заданного целого числа `n` найдите число, следующее за `n`, кратное `10`.
 *
 * ## Входные данные
 *
 * Одно целое число `n`.
 *
 * ## Выходные данные
 *
 * Вывести число, следующее за `n`, кратное `10`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8897">Следующее число 2<a>
 */
interface Problem8897K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8897K {
        /**
         * Даёт `100` баллов из `103`.
         */
        simple {
            override fun main(args: Array<String>) {
                val src = readln().toInt()
                val inc = if (src >= 0) (10 - src % 10) else -(src % 10)
                val next = src + inc
                println(next)
            }
        },

        /**
         * Даёт `100` баллов из `103`.
         */
        bigInt {
            override fun main(args: Array<String>) {
                val src = readln().toBigInteger()
                val inc = if (src >= BigInteger.ZERO) (BigInteger.TEN - src % BigInteger.TEN) else -(src % BigInteger.TEN)
                val next = src + inc
                println(next)
            }
        },
    }
}
