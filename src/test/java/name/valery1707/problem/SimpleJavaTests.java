package name.valery1707.problem;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleJavaTests {

    @Test
    void test1() {
        assertThat("").isBlank();
        assertThat(List.of("1")).hasSize(1);
    }

}
