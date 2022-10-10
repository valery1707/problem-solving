package name.valery1707.problem.e.olymp

/**
 * # Первая цифра числа
 *
 * Найти первую цифру целого числа.
 * Отсчёт начинать с наивысшего разряда.
 *
 * ## Входные данные
 *
 * Одно целое **64-х** разрядное число, содержащее не менее одной цифры.
 * Число может быть отрицательным.
 *
 * ## Выходные данные
 *
 * Выведите первую цифру заданного числа.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8243">Первая цифра числа<a>
 */
interface Problem8243K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8243K {
        simple {
            override fun main(args: Array<String>) {
                val first = readln().trim().trimStart('-')[0]
                println(first)
            }
        },
    }
}
