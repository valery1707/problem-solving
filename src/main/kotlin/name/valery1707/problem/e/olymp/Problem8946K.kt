package name.valery1707.problem.e.olymp

/**
 * # Шаблон
 *
 * По заданному натуральному числу `n` вывести изображение размером `n * n`, образованное символами звездочка и пробел как показано в примере.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n`.
 *
 * ## Выходные данные
 *
 * Вывести изображение `n * n`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8946">Шаблон</a>
 */
interface Problem8946K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8946K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                (1..n).map {
                    when (it % 2) {
                        1 -> "* "
                        else -> " *"
                    }
                        .repeat(1 + n / 2)
                        .substring(0, n)
                }
                    .forEach(::println)
            }
        },
    }
}
