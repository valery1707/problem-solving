package name.valery1707.problem.e.olymp

/**
 * # Наименьшее из больших
 *
 * Дано целое число `n`. Среди целых чисел больших `n` и кратных `7` найти наименьшее.
 *
 * ## Входные данные
 *
 * Одно целое число `n`.
 *
 * ## Выходные данные
 *
 * Вывести наименьшее число, большее `n` и кратное `7`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8900">Наименьшее из больших</a>
 */
interface Problem8900K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8900K {
        simple {
            override fun main(args: Array<String>) {
                val src = readln().toInt()
                val inc = if (src >= 0) (7 - src % 7) else -(src % 7)
                val next = src + if (inc > 0) inc else 7
                println(next)
            }
        },
    }
}
