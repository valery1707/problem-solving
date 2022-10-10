package name.valery1707.problem.e.olymp

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * # Периметр треугольника
 *
 * По заданным длинам сторон треугольника найдите его периметр.
 *
 * ## Входные данные
 *
 * Состоит из нескольких тестов.
 * Каждая строка содержит длины трех сторон треугольника.
 * Длины сторон - положительные действительные числа.
 *
 * ## Выходные данные
 *
 * Для каждого теста вывести в отдельной строке периметр треугольника с **4** десятичными знаками.
 *
 * @see <a href="https://www.eolymp.com/ru/problems/8687">Периметр треугольника</a>
 */
interface Problem8687K : ProblemConsole {
    @Suppress("EnumEntryName", "unused")
    enum class Implementation : Problem8687K {
        sequenceBigDecimal {
            override fun main(args: Array<String>) {
                generateSequence(::readlnOrNull)
                    .filter(String::isNotBlank)
                    .map {
                        (it
                            .splitToSequence(' ')
                            .take(3)
                            .map(String::toBigDecimal)
                            .reduceOrNull(BigDecimal::plus) ?: BigDecimal.ZERO)
                            .setScale(4, RoundingMode.HALF_UP)
                    }
                    .forEach(::println)
            }
        },
        sequenceDouble {
            override fun main(args: Array<String>) {
                generateSequence(::readlnOrNull)
                    .filter(String::isNotBlank)
                    .map {
                        it
                            .splitToSequence(' ')
                            .take(3)
                            .map(String::toDouble)
                            .reduceOrNull(Double::plus) ?: 0.0
                    }
                    .forEach { System.out.printf("%.04f%n", it) }
            }
        },
    }
}
