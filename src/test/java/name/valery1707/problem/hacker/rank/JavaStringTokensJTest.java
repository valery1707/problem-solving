package name.valery1707.problem.hacker.rank;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static name.valery1707.problem.TestUtilsJ.withinConsole;
import static org.assertj.core.api.Assertions.assertThat;

class JavaStringTokensJTest {

    @ParameterizedTest
    @ImplementationSource(
        implementation = JavaStringTokensJ.Implementation.class,
        csv = @CsvSource({
            "'He is a very very good boy, isn't he?'," +
                "10|He|is|a|very|very|good|boy|isn|t|he",
            "'Hello, thanks for attempting this problem! Hope it will help you to learn java! Good luck and have a nice day!'," +
                "21|Hello|thanks|for|attempting|this|problem|Hope|it|will|help|you|to|learn|java|Good|luck|and|have|a|nice|day",
            "'           YES      leading spaces        are valid,    problemsetters are         evillllll'," +
                "8|YES|leading|spaces|are|valid|problemsetters|are|evillllll",
        })
    )
    void test1(Implementation<JavaStringTokensJ> variant, String input, String expected) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected);
    }

}
