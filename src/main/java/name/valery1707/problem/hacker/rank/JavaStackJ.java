package name.valery1707.problem.hacker.rank;

import name.valery1707.problem.e.olymp.ProblemConsole;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>Java Stack</h1>
 * <p>
 * In computer science, a stack or LIFO (last in, first out) is an abstract data type that serves as a collection of elements, with two principal operations:
 * push, which adds an element to the collection, and
 * pop, which removes the last element that was added. (Wikipedia)
 * <p>
 * A string containing only parentheses is balanced if the following is true:
 * 1. if it is an empty string
 * 2. if A and B are correct, AB is correct,
 * 3. if A is correct, (A) and {A} and [A] are also correct.
 * <p>
 * Examples of some correctly balanced strings are: "{}()", "[{()}]", "({()})"
 * <p>
 * Examples of some unbalanced strings are: "{}(", "({)}", "[[", "}{" etc.
 * <p>
 * Given a string, determine if it is balanced or not.
 *
 * <h3>Input Format</h3>
 * <p>
 * There will be multiple lines in the input file, each having a single non-empty string.
 * You should read input till end-of-file.
 * <p>
 * The part of the code that handles input operation is already provided in the editor.
 *
 * <h3>Output Format</h3>
 * <p>
 * For each case, print 'true' if the string is balanced, 'false' otherwise.
 *
 * @see <a href="https://www.hackerrank.com/challenges/java-stack/problem">Java Stack</a>
 */
@SuppressWarnings("scwloggingslf4j_SLF4JLoggingSystem.out")
public interface JavaStackJ extends ProblemConsole {

    @Override
    default void main(@NotNull String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            while (scan.hasNext()) {
                String input = scan.next();
                System.out.println(isBalanced(input));
            }
        }
    }

    boolean isBalanced(String input);

    enum Implementation implements JavaStackJ {
        @SuppressWarnings("EnhancedSwitchMigration")
        deque_java8 {
            @Override
            public boolean isBalanced(String input) {
                Deque<Character> stack = new ArrayDeque<>();
                for (int i = 0; i < input.length(); i++) {
                    char curr = input.charAt(i);
                    switch (curr) {
                        case '{':
                        case '[':
                        case '(':
                            stack.push(curr);
                            break;
                        case '}':
                        case ']':
                        case ')':
                            if (stack.isEmpty()) {
                                return false;
                            }
                            char prev = stack.pop();
                            if (
                                (curr == '}' && prev != '{') ||
                                    (curr == ']' && prev != '[') ||
                                    (curr == ')' && prev != '(')
                            ) {
                                return false;
                            }
                            break;
                    }
                }
                return stack.isEmpty();
            }
        },
        deque_java17 {
            @Override
            public boolean isBalanced(String input) {
                Deque<Character> stack = new ArrayDeque<>();
                for (int i = 0; i < input.length(); i++) {
                    char curr = input.charAt(i);
                    switch (curr) {
                        case '{', '[', '(' -> stack.push(curr);
                        case '}', ']', ')' -> {
                            if (stack.isEmpty()) {
                                return false;
                            }
                            char prev = stack.pop();
                            if (
                                (curr == '}' && prev != '{') ||
                                    (curr == ']' && prev != '[') ||
                                    (curr == ')' && prev != '(')
                            ) {
                                return false;
                            }
                        }
                    }
                }
                return stack.isEmpty();
            }
        },
        list_java17 {
            @Override
            public boolean isBalanced(String input) {
                List<Character> stack = new ArrayList<>();
                for (int i = 0; i < input.length(); i++) {
                    char curr = input.charAt(i);
                    switch (curr) {
                        case '{', '[', '(' -> stack.add(curr);
                        case '}', ']', ')' -> {
                            if (stack.isEmpty()) {
                                return false;
                            }
                            char prev = stack.remove(stack.size() - 1);
                            if (
                                (curr == '}' && prev != '{') ||
                                    (curr == ']' && prev != '[') ||
                                    (curr == ')' && prev != '(')
                            ) {
                                return false;
                            }
                        }
                    }
                }
                return stack.isEmpty();
            }
        },
    }

}
