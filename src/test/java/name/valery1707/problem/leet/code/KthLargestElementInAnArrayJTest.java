package name.valery1707.problem.leet.code;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class KthLargestElementInAnArrayJTest {

    private static final Pattern COMMA = Pattern.compile(",");

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = KthLargestElementInAnArrayJ.Implementation.class,
        csv = @CsvSource(delimiter = '|', value = {
            "Example 1|3,2,1,5,6,4|2|5",
            "Example 2|3,2,3,1,2,4,5,5,6|4|4",
        })
    )
    void test1(Implementation<KthLargestElementInAnArrayJ> variant, String description, String numsStr, int k, int expected) {
        var nums = COMMA.splitAsStream(numsStr).mapToInt(Integer::parseInt).toArray();
        assertThat(variant.get().findKthLargest(nums, k)).as(description).isEqualTo(expected);
    }

}
