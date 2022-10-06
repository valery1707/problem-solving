package name.valery1707.problem.leet.code;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

import static java.time.Duration.ofSeconds;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.assertj.core.api.Assertions.assertThat;

class ConstructTargetArrayWithMultipleSumsJTest {

    private static final Pattern COMMA = Pattern.compile(",");

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = ConstructTargetArrayWithMultipleSumsJ.Implementation.class,
        csv = @CsvSource(delimiter = '|', value = {
            "Example 1|9,3,5|true",
            "Example 2|1,1,1,2|false",
            "Example 3|8,5|true",
            "Self 1|1|true",
            "Self 2|2|false",
            "Test 1|1,1000000000|true",
            "Discussion 1|5,33,17,113,57|true",
            "Discussion 2|3,5,33|true",
        })
    )
    void test1(Implementation<ConstructTargetArrayWithMultipleSumsJ> variant, String description, String arrayStr, boolean expected) {
        var array = COMMA.splitAsStream(arrayStr).mapToInt(Integer::parseInt).toArray();
        assertThat(supplyAsync(() -> variant.get().isPossible(array)))
            .as(description)
            .succeedsWithin(ofSeconds(5), InstanceOfAssertFactories.BOOLEAN)
            .isEqualTo(expected);
    }

}
