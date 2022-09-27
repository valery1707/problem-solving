package name.valery1707.problem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimpleKotlinTests {
    @Test
    internal fun test1() {
        assertThat("").isBlank
        assertThat(listOf("1")).hasSize(1)
    }
}
