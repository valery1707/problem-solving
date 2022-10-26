package name.valery1707.problem.hacker.rank;

import name.valery1707.problem.e.olymp.ProblemConsole;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>Java String Tokens</h1>
 * <p>
 * Given a string, {@code s}, matching the regular expression {@code [A-Za-z !,?._'@]+}, split the string into tokens.
 * We define a token to be one or more consecutive English alphabetic letters.
 * Then, print the number of tokens, followed by each token on a new line.
 * <p>
 * <b>Note:</b> You may find the <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#split-java.lang.String-">String.split</a> method
 * helpful in completing this challenge.
 *
 * <h3>Input Format</h3>
 * <p>
 * A single string, {@code s}.
 *
 * <h3>Constraints</h3>
 * <ul>
 * <li>{@code 1 <= length of s <= 4*10^5}</li>
 * <li>{@code s} is composed of any of the following:
 * English alphabetic letters,
 * blank spaces,
 * exclamation points ({@code !}),
 * commas ({@code ,}),
 * question marks ({@code ?}),
 * periods ({@code .}),
 * underscores ({@code _}),
 * apostrophes ({@code '}),
 * and at symbols ({@code @}).</li>
 * </ul>
 *
 * <h3>Output Format</h3>
 * <p>
 * On the first line, print an integer, {@code n}, denoting the number of tokens in string @{code s} (they do not need to be unique).
 * Next, print each of the {@code n} tokens on a new line in the same order as they appear in input string {@code s}.
 *
 * @see <a href="https://www.hackerrank.com/challenges/java-string-tokens/problem">Java String Tokens</a>
 */
public interface JavaStringTokensJ extends ProblemConsole {

    @SuppressWarnings("scwloggingslf4j_SLF4JLoggingSystem.out")
    enum Implementation implements JavaStringTokensJ {
        forIsLetter {
            @Override
            public void main(@NotNull String[] args) {
                try (Scanner scan = new Scanner(System.in)) {
                    String s = scan.nextLine();
                    List<String> tokens = new ArrayList<>();
                    StringBuilder token = new StringBuilder();
                    for (int i = 0; i < s.length(); i++) {
                        char ch = s.charAt(i);
                        if (Character.isLetter(ch)) {
                            token.append(ch);
                        } else {
                            tokens.add(token.toString());
                            token = new StringBuilder();
                        }
                    }
                    tokens.add(token.toString());
                    tokens.removeIf(String::isEmpty);
                    System.out.println(tokens.size());
                    tokens.forEach(System.out::println);
                }
            }
        },
    }

}
