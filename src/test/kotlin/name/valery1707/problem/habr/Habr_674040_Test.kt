package name.valery1707.problem.habr

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * При разработке на `Kotlin` часто приходится использовать списки (`List`, `MutableList` и другие), а также операторы для них.
 * Каждый раз, заходя в [документацию](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/), я убеждаюсь, что операторов очень много,
 * а в их использовании есть тонкости.
 *
 * Многие коллеги говорят, что в этой теме они разбираются на 100%.
 * Давайте проверим, насколько хорошо вы владеете операторами для списков.
 * Я подготовил 14 логических задач разной сложности, попробуйте их решить.
 *
 * ### Правила
 *
 * Есть задание — лист элементов.
 * Ваша задача вставить один оператор, чтобы получилось ожидаемое значение.
 *
 * ### Замечания и допущения
 *
 * * Хочу отметить, что из-за разнообразия операторов и по стечению обстоятельств решений может быть несколько.
 *   Например, для задачи выше также правильными были бы решения `last()`, `get(0)` и так далее.
 * * Во всех задачах использован один и тот же список.
 * * Не нужно во всех задачах искать логику с точки зрения решения бизнес-задач.
 *   Иногда задачи нарочно составлены с ошибками с точки зрения адекватности, сбивающими с толку.
 * * Эти упражнения помогут вам не только размять свой мозг, но и узнать о некоторых особенностях операторов, с которыми вы, возможно, не сталкивались.
 * * Задачи я старался располагать от простых к сложным. Но при решении нужно учитывать, что у каждого свой уровень и опыт.
 *
 * @see <a href="https://habr.com/ru/company/ru_mts/blog/674040/">14 задач по Kotlin lists, которые заставят вас подумать</a>
 */
@Suppress("ClassName")
internal class Habr_674040_Test {

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("testFuncs")
    internal fun <T> testFuncs(description: String, expected: String, missed: (List<Int>) -> T, finish: (T) -> Any?) {
        val actual = try {
            listOf(1, 3, 3, 2, 4, 1).let(missed).let(finish).toString()
        } catch (e: Exception) {
            e.javaClass.name
        }
        assertThat(actual).`as`(description).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun testFuncs(): Iterable<Arguments> {
            //Ответ с `:` я придумал сам, а с `!` почерпнул из статьи
            return listOf(
                testFunc("sample: first()", "1", { src -> src.first() }),
                testFunc("sample: last()", "1", { src -> src.last() }),
                testFunc("sample: min()", "1", { src -> src.minOrNull() }),
                testFunc("sample: get(0)", "1", { src -> src[0] }),
                // Легкий уровень
                testFunc("task 1: size", "6", { src -> src.size }),
                testFunc("task 1! count()", "6", { src -> src.count() }),
                testFunc("task 2: takeLast(2)", "5", { src -> src.takeLast(2) }, { it.sum() }),
                testFunc("task 3: asSequence()", "14", { src -> src.asSequence() }, { it.sum() }),
                testFunc("task 4: distinct()", "2.5", { src -> src.distinct() }, { it.average() }),
                testFunc("task 5: map(const)", "8.0", { src -> src.map { 8 } }, { it.average() }),
                // Средний уровень
                testFunc("task 6: map()", "8", { src -> src.map { if (it >= 2) 1 else 2 } }, { it.sum() }),
                testFunc("task 6! filterIndexed", "8", { src -> src.filterIndexed { index, _ -> index % 2 == 0 } }, { it.sum() }),
                testFunc("task 7! reduce", "72", { src -> src.reduce { acc, it -> acc * it } }),
                testFunc("task 8: elementAtOrNull(10)", "null", { src -> src.elementAtOrNull(10) }),
                testFunc("task 8: getOrNull(10)", "null", { src -> src.getOrNull(10) }),
                testFunc("task 8: firstOrNull(it < 0)", "null", { src -> src.firstOrNull { it < 0 } }),
                testFunc("task 9: forEach", "kotlin.Unit", { src -> src.forEach { it.toString() } }),
                testFunc("task 10! indices", "15", { src -> src.indices }, { it.sum() }),
                testFunc("task 11: drop(many)", "java.util.NoSuchElementException", { src -> src.drop(10) }, { it.first() }),
                testFunc("task 11! take(0)", "java.util.NoSuchElementException", { src -> src.take(0) }, { it.first() }),
                // Сложный уровень
                testFunc("task 12: mapIndexed", "29", { src -> src.mapIndexed { idx, it -> idx + it } }, { it.sum() }),
                testFunc("task 13! flatMap", "27", { src -> src.flatMap { 0.rangeTo(it) } }, { it.sum() }),
                testFunc("task 14: filter", "6", { src -> src.filter { it == 3 } }, { it.sum() }),
                testFunc("task 14: distinctBy", "6", { src -> src.distinctBy { it % 3 } }, { it.sum() }),
            )
        }

        private fun <T> testFunc(description: String, expected: String, missed: (List<Int>) -> T, finish: (T) -> Any? = { it }): Arguments = arguments(
            description, expected, missed, finish
        )
    }
}
