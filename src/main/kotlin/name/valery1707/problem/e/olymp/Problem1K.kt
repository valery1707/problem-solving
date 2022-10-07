package name.valery1707.problem.e.olymp

/**
 * # Простая задача
 *
 * Программа считывает двузначное число и выводит через пробел каждую цифру отдельно.
 *
 * ## Входные данные
 *
 * Натуральное число из промежутка от `10` до `99` включительно.
 *
 * ## Выходные данные
 *
 * Два одноцифровых числа, разделенных пробелом.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/1">Простая задача<a>
 */
interface Problem1K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem1K {
        scanner {
            override fun main(args: Array<String>) {
                val scan = java.util.Scanner(System.`in`)
                val src = scan.nextInt()
                println("${src / 10} ${src % 10}")
            }
        },
        read {
            override fun main(args: Array<String>) {
                val src = readln().toInt()
                println("${src / 10} ${src % 10}")
            }
        },
    }
}
