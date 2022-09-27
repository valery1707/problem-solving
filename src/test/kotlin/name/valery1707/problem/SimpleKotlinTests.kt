package name.valery1707.problem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SimpleKotlinTests {

    @Test
    internal fun test1() {
        assertThat("").isBlank
        assertThat(listOf("1")).hasSize(1)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "1,1",
        "11,2",
        "111,3",
    ])
    internal fun test2(param: String, expected: Int) {
        assertThat(param).hasSize(expected)
    }

}
