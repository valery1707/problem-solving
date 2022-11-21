package name.valery1707.problem.leet.code;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class FurthestBuildingYouCanReachJTest {

    private static final Pattern COMMA = Pattern.compile(",");

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = FurthestBuildingYouCanReachJ.Implementation.class,
        csv = @CsvSource(delimiter = '|', value = {
            "Example 1|4,2,7,6,9,14,12|5|1|4",
            "Example 2|4,12,2,7,3,18,20,3,19|10|2|7",
            "Example 3|14,3,19,3|17|0|3",
            "Self 1|14|1|1|0",
            "Self 2|1,5,7,9|4|1|3",
            "Test 1|1,13,1,1,13,5,11,11|10|8|7",
        }),
        csvFiles = @CsvFileSource(delimiter = '|', numLinesToSkip = 1, maxCharsPerColumn = 8192, resources = "/leet/code/FurthestBuildingYouCanReach.csv")
    )
    void test1(Implementation<FurthestBuildingYouCanReachJ> variant, String description, String heightsStr, int bricks, int ladders, int expected) {
        var heights = COMMA.splitAsStream(heightsStr).mapToInt(Integer::parseInt).toArray();
        assertThat(variant.get().furthestBuilding(heights, bricks, ladders)).as(description).isEqualTo(expected);
    }

}
