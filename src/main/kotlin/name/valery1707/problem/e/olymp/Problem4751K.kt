package name.valery1707.problem.e.olymp

/**
 * # Диагонали
 *
 * В квадратной таблице `n × n` подсчитать сумы чисел, стоящих на главной и побочной диагоналях.
 *
 * ## Входные данные
 *
 * Вводится число `n` (`1 ≤ n ≤ 500`), а затем матрица `n × n`.
 * Элементы матрицы - числа по модулю не больше `10^5`.
 *
 * Для того, чтобы понять, как какая диагональ называется, внимательно присмотритесь ко второму примеру.
 *
 * ## Выходные данные
 *
 * Вывести сумму чисел сначала на главной, а затем на побочной диагонали.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/4751">Диагонали</a>
 */
interface Problem4751K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem4751K {
        simple {
            override fun main(args: Array<String>) {
                val n = readln().toInt()
                val matrix = generateSequence(::readlnOrNull)
                    .take(n)
                    .map { it.splitToSequence(' ').take(n).map(String::toInt).toList() }
                    .toList()
                val sum1 = (1..n).sumOf { matrix[it - 1][it - 1] }
                val sum2 = (1..n).sumOf { matrix[n - it][it - 1] }
                println("$sum1 $sum2")
            }
        },
    }
}
