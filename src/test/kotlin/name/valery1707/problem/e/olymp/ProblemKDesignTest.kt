package name.valery1707.problem.e.olymp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.regex.Pattern
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlin.streams.asStream

internal class ProblemKDesignTest {

    /**
     * Документация должна содержать заголовок и он же должен быть указан в ссылке.
     */
    @DisplayName("Problem name")
    @ParameterizedTest(name = "[{index}] Problem name in JavaDoc: {0}")
    @MethodSource("main")
    internal fun test1(name: String, text: String) {
        assertThat(text).`as`(name).containsPattern(DOC_TITLE.toPattern())
        val title = DOC_TITLE.findAll(text).map { it.groupValues[1] }.toList()
        assertThat(title).`as`("Title count").hasSize(1)
        val link = ">${title[0].quoted()}</a>".toRegex()
        assertThat(text).`as`("Link present").containsPattern(link.toPattern())
        assertThat(link.findAll(text).asStream()).`as`("Link count").hasSize(1)
    }

    /**
     * Реализация должна указывать на тот же индекс задачи, что и сам файл.
     */
    @DisplayName("Problem index (main)")
    @ParameterizedTest(name = "[{index}] Problem index in implementation: {0}")
    @MethodSource("main")
    internal fun test2(name: String, text: String) {
        val index = NAME_REGEX.find(name)!!.groupValues[1]
        assertThat(text)
            .containsPattern("interface Problem${index}K")
            .containsPattern("enum class Implementation : Problem${index}K \\{")
    }

    /**
     * Тесты должны проверять реализацию, к которой отсылает имя тестов.
     */
    @DisplayName("Problem index (test)")
    @ParameterizedTest(name = "[{index}] Problem index in test: {0}")
    @MethodSource("test")
    internal fun test3(name: String, text: String) {
        val index = NAME_REGEX.find(name)!!.groupValues[1]
        assertThat(text)
            .containsPattern("implementation = \\[Problem${index}K.Implementation::class],")
            .containsPattern("variant: Implementation<Problem${index}K>")
    }

    companion object {
        val DOC_TITLE = "^ \\* # (.+)$".toRegex(RegexOption.MULTILINE)

        @JvmStatic
        fun main(): Iterable<Arguments> = scan("main")

        @JvmStatic
        fun test(): Iterable<Arguments> = scan("test")

        private fun Path.find(maxDepth: Int = Int.MAX_VALUE, matcher: (Path, BasicFileAttributes) -> Boolean) = java.nio.file.Files
            .find(this, maxDepth, matcher)

        private val NAME_REGEX = "^Problem(\\d+)".toRegex()

        private fun scan(type: String): Iterable<Arguments> = Path("src", type, "kotlin")
            .find { path, att -> path.nameWithoutExtension.contains(NAME_REGEX) && att.isRegularFile }
            .use { files ->
                files
                    .map { file ->
                        Arguments.of(file.nameWithoutExtension, file.readText())
                    }
                    .toList()
            }

        fun String.quoted() = Pattern.quote(this)
    }

}
