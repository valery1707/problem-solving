package name.valery1707.problem;

import java.util.Random;

import static java.util.stream.Collectors.joining;

/**
 * Рассматривается сравнение вариантов конкатенации строк в коде с учётом производительности и читаемости.
 * <p>
 * Из-за низкой читаемости в рассмотрение не попадают варианты с {@link StringBuilder} и {@link java.util.StringJoiner StringJoiner}.
 */
public interface StringConcatenationJ {

    String accept(String lhs, String rhs);


    enum Implementation implements StringConcatenationJ {
        plus {
            @Override
            public String accept(String lhs, String rhs) {
                return lhs + "_" + rhs;
            }
        },
        join {
            @Override
            public String accept(String lhs, String rhs) {
                return String.join("_", lhs, rhs);
            }
        },
        concat {
            @Override
            public String accept(String lhs, String rhs) {
                return lhs.concat("_").concat(rhs);
            }
        },
    }

    static String generate(Random random, int len) {
        return random
            .ints(1, 10)
            .limit(len).mapToObj(String::valueOf)
            .collect(joining());
    }

}
