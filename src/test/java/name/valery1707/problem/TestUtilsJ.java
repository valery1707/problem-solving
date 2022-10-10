package name.valery1707.problem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

public final class TestUtilsJ {

    private TestUtilsJ() {}

    private static final Map<String, Pattern> PATTERN_CACHE = new HashMap<>();

    private static Pattern quotedPattern(String pattern) {
        return PATTERN_CACHE.computeIfAbsent(pattern, __ -> Pattern.compile(Pattern.quote(pattern)));
    }

    public static String withinConsole(String newLineDelimiter, String inputRaw, Runnable test) {
        var oldIn = System.in;
        var oldOut = System.out;
        try {
            var input = quotedPattern(newLineDelimiter)
                .splitAsStream(inputRaw)
                .collect(joining(lineSeparator()))
                .getBytes(UTF_8);
            System.setIn(new ByteArrayInputStream(input));

            var output = new ByteArrayOutputStream();
            try (var outStream = new PrintStream(output)) {
                System.setOut(outStream);
                test.run();
            }

            return quotedPattern(lineSeparator())
                .splitAsStream(output.toString(UTF_8))
                .collect(joining(newLineDelimiter));
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }

    public static String withinConsole(String newLineDelimiter, String inputRaw, Consumer<String[]> test, String... args) {
        return withinConsole(newLineDelimiter, inputRaw, () -> test.accept(args));
    }

}
