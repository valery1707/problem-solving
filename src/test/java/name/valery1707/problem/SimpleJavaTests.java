package name.valery1707.problem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleJavaTests {

    @Test
    void test1() {
        assertThat("").isBlank();
        assertThat(List.of("1")).hasSize(1);
    }

    @ParameterizedTest
    @CsvSource({
        "1,1",
        "11,2",
        "111,3",
    })
    void test2(String param, int expected) {
        assertThat(param).hasSize(expected);
    }

}
