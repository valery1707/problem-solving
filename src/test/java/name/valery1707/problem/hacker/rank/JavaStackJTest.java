package name.valery1707.problem.hacker.rank;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static name.valery1707.problem.TestUtilsJ.withinConsole;
import static org.assertj.core.api.Assertions.assertThat;

class JavaStackJTest {

    @ParameterizedTest
    @ImplementationSource(
        implementation = JavaStackJ.Implementation.class,
        csv = @CsvSource({
            //Test case 0 - original
            "true|true|false|true,{}()|({()})|{}(|[]",
            //Test case 0 - separated
            "true,{}()",
            "true,({()})",
            "false,{}(",
            "true,[]",
            //Test case 1
            "true,({}[])",
            "false,(({()})))",
            "true,({(){}()})()({(){}()})(){()}",
            "false,{}()))(()()({}}{}",
            "false,}}}}",
            "false,))))",
            "false,{{{",
            "false,(((",
            "true,[]{}(){()}((())){{{}}}{()()}{{}{}}",
            "true,[[]][][]",
            "false,}{",
            //Self-invented
            "true,()",
            "false,(}",
            "false,(]",
            "false,{)",
            "true,{}",
            "false,{]",
            "false,[)",
            "false,[}",
            "true,[]",
        })
    )
    void test1(Implementation<JavaStackJ> variant, String expected, String input) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected);
    }

}
