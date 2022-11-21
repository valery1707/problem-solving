package name.valery1707.problem.e.olymp

/**
 * # Матрица
 *
 * Заданы два натуральных числа `n` и `m`.
 * Вывести матрицу, состоящую из `n` строк и `m` столбцов, заполненную натуральными числами от `1` до `n * m`, как показано в примере.
 *
 * ## Входные данные
 *
 * Два натуральных числа `n` и `m` (`1 ≤ n, m ≤ 100`).
 *
 * ## Выходные данные
 *
 * Вывести требуемую матрицу.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8941">Матрица</a>
 */
interface Problem8941K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8941K {
        chunked {
            override fun main(args: Array<String>) {
                val (n, m) = readln().split(' ').take(2).map(String::toInt)
                val matrix = (1..n * m).chunked(m)
                matrix.forEach { line -> println(line.joinToString(separator = " ")) }
            }
        },
    }
}
