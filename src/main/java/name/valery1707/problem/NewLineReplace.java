package name.valery1707.problem;

import java.util.regex.Pattern;

public interface NewLineReplace {

    String REGEX = "\r\n|\n|\r";
    Pattern PATTERN = Pattern.compile(REGEX);
    String REPLACEMENT = " ";

    String replace(String source);

    enum Implementation implements NewLineReplace {
        replace {
            @Override
            public String replace(String source) {
                return source.replace("\r\n", REPLACEMENT).replace("\n", REPLACEMENT).replace("\r", REPLACEMENT);
            }
        },
        regexpRaw {
            @Override
            public String replace(String source) {
                return source.replaceAll(REGEX, REPLACEMENT);
            }
        },
        regexpCompiled {
            @Override
            public String replace(String source) {
                return PATTERN.matcher(source).replaceAll(REPLACEMENT);
            }
        },
    }

}
