package name.valery1707.problem.leet.code;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CourseSchedule3JTest {

    private static final Pattern COURSE_INFO = Pattern.compile("\\[((\\d+,?)+)]");

    private static final Set<String> TEST1_IGNORED = Set.of(
        "streamSortReduce(Test 1)"
    );

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = CourseSchedule3J.Implementation.class,
        csv = @CsvSource(delimiter = '|', value = {
            "Example 1|[[100,200],[200,1300],[1000,1250],[2000,3200]]|3",
            "Example 2|[[1,2]]|1",
            "Example 3|[[3,2],[4,3]]|0",
            "Test 1|[[10,20],[4,13],[4,4],[3,11],[3,5],[3,5]]|4",
            "Test 2|[[5,15],[3,19],[6,7],[2,10],[5,16],[8,14],[10,11],[2,19]]|5",
            "Test 2|[[6,7],[2,10],[2,19],[5,15],[3,19]-[5,16],[8,14],[10,11]]|5",
        })
    )
    @DisabledIf("test1IsIgnored")
    void test1(Implementation<CourseSchedule3J> variant, String description, String coursesRaw, int expected) {
        var courses = COURSE_INFO
            .matcher(coursesRaw).results()
            .map(it -> Stream.of(it.group(1).split(",")).mapToInt(Integer::parseInt).toArray())
            .toArray(int[][]::new);
        assertThat(variant.get().scheduleCourse(courses)).as(description).isEqualTo(expected);
    }

    static boolean test1IsIgnored(ExtensionContext context) {
        return TEST1_IGNORED.contains(context.getDisplayName().substring(context.getDisplayName().indexOf(']') + 2));
    }

}
