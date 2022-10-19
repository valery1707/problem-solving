package name.valery1707.problem;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class NewLineReplaceTest {

    @ParameterizedTest
    @ImplementationSource(
        implementation = NewLineReplace.Implementation.class,
        csv = @CsvSource({
            // * `L` treated as `\n` (Unix/Linux)
            // * `W` treated as `\r\n` (DOS/Windows)
            // * `M` treated as `\r` (Mac Classic prior to Mac OS X)
            "123,'123'",
            "123L,'123 '",
            "123W,'123 '",
            "123M,'123 '",
            "123L456,'123 456'",
            "123W456,'123 456'",
            "123M456,'123 456'",
        })
    )
    void test1(Implementation<NewLineReplace> variant, String source, String expected) {
        source = source.replace("L", "\n").replace("W", "\r\n").replace("M", "\r");
        assertThat(variant.get().replace(source)).isEqualTo(expected);
    }

}
