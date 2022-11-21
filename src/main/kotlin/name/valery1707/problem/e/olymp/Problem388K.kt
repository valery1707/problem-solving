package name.valery1707.problem.e.olymp

/**
 * # Превращение
 *
 * Возьмем какое-нибудь натуральное число `n`.
 * Будем изменять его следующим образом:
 * * если число четное, то разделим его на 2
 * * если нечетное, прибавим 1.
 *
 * После нескольких таких изменений мы всегда получаем число `1`.
 * Например, из числа `11` получается число `12`, затем `6`, `3`, `4`, `2` и наконец `1`.
 * Таким образом, для получения `1` из `11` нужно проделать `6` изменений.
 *
 * По заданному натуральному числу найти количество его изменений до получения `1`.
 *
 * ## Входные данные
 *
 * Одно натуральное число `n` (`1 ≤ n ≤ 10^9`).
 *
 * ## Выходные данные
 *
 * Вывести количество изменений числа `n` до получения `1`.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/388">Превращение</a>
 */
interface Problem388K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem388K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                val count = generateSequence(n) { if (it % 2 == 0) it / 2 else it + 1 }
                    .takeWhile { it > 1 }
                    .count()
                println(count)
            }
        },
    }
}
