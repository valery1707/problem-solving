package name.valery1707.problem.e.olymp

/**
 * # Вторая цифра числа
 *
 * Найдите вторую цифру целого числа.
 * Отсчёт начинать с наивысшего разряда.
 *
 * ## Входные данные
 *
 * Одно целое **64-х** разрядное число, содержащее не менее двух цифр.
 * Число может быть отрицательным.
 *
 * ## Выходные данные
 *
 * Выведите вторую цифру заданного числа.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8243">Вторая цифра числа</a>
 */
interface Problem1605K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1605K {
        simple {
            override fun main(args: Array<String>) {
                val second = readln().trim().trimStart('-')[1]
                println(second)
            }
        },
    }
}
