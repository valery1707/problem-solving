package name.valery1707.problem.leet.code.learn.queue_stack;

import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class MyCircularQueueJTest {

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = MyCircularQueueJ.Implementation.class,
        csv = @CsvSource(delimiter = '|', value = {
            "article1" +
                "|'MyCircularQueue', 'enQueue', 'enQueue', 'enQueue', 'enQueue', 'enQueue', isFull, 'Front', 'Rear', 'deQueue', 'deQueue', 'enQueue', 'enQueue', 'isFull', 'deQueue', 'deQueue', 'deQueue', 'deQueue', 'deQueue', 'isEmpty' " +
                "|[5], [5], [13], [8], [2], [10], [], [], [], [], [], [23], [6], [], [], [], [], [], [], []" +
                "|null, true, true, true, true, true, true, 5, 10, true, true, true, true, true, true, true, true, true, true, true",
            "test1" +
                "|'MyCircularQueue', 'enQueue', 'enQueue', 'enQueue', 'enQueue', 'Rear', 'isFull', 'deQueue', 'enQueue', 'Rear'" +
                "|[3], [1], [2], [3], [4], [], [], [], [4], []" +
                "|null, true, true, true, false, 3, true, true, true, 4",
        })
    )
    void test1(Implementation<Function<Integer, MyCircularQueueJ>> variant, String description, String actionStr, String argStr, String expectedStr) {
        var comma = Pattern.compile(",");
        var actions = comma.splitAsStream(actionStr).map(s -> s.replace("'", "")).map(String::trim).toList();
        @SuppressWarnings("ConstantConditions")
        var args = comma.splitAsStream(argStr).map(s -> s.replaceAll("[\\[\\]]", "")).map(String::trim).map(s -> s.isEmpty() ? null : Integer.valueOf(s)).toList();
        var expects = comma.splitAsStream(expectedStr).map(String::trim).toList();
        assertThat(actions).as(description).hasSameSizeAs(args).hasSameSizeAs(expects);

        MyCircularQueueJ queue = null;
        for (int i = 0; i < actions.size(); i++) {
            Object actual = switch (actions.get(i)) {
                case "MyCircularQueue" -> {
                    queue = variant.get().apply(args.get(i));
                    yield "null";
                }
                case "enQueue" -> requireNonNull(queue).enQueue(args.get(i));
                case "deQueue" -> requireNonNull(queue).deQueue();
                case "Front" -> requireNonNull(queue).Front();
                case "Rear" -> requireNonNull(queue).Rear();
                case "isEmpty" -> requireNonNull(queue).isEmpty();
                case "isFull" -> requireNonNull(queue).isFull();
                default -> throw new IllegalStateException("Invalid action: " + actions.get(i));
            };
            assertThat(actual)
                .as("%s: %s(%s) == %s", i, actions.get(i), args.get(i), expects.get(i))
                .hasToString(expects.get(i));
        }
    }

}
