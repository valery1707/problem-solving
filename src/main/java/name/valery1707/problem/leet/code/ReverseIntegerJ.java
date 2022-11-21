package name.valery1707.problem.leet.code;

/**
 * Given a signed 32-bit integer {@code x}, return {@code x} with its digits reversed.
 * If reversing {@code x} causes the value to go outside the signed 32-bit integer range {@code [-231, 231 - 1]}, then return {@code 0}.
 * <p>
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 * <p>
 * Constraints:
 * <ul>
 * <li>{@code -2^31 <= x <= 2^31 - 1}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/reverse-integer/">Reverse Integer</a>
 */
public interface ReverseIntegerJ {

    int reverse(int value);

    enum Implementation implements ReverseIntegerJ {
        math {
            @Override
            public int reverse(int value) {
                int result = 0;
                try {
                    while (value != 0) {
                        result = Math.addExact(Math.multiplyExact(result, 10), value % 10);
                        value = value / 10;
                    }
                } catch (ArithmeticException ignored) {
                    return 0;
                }
                return result;
            }
        },

        charArray {
            @Override
            public int reverse(int value) {
                char[] digits = String.valueOf(value).toCharArray();
                int min = digits[0] == '-' ? 1 : 0;
                int max = digits.length - 1;
                int flipCount = (max - min + 1) / 2;
                for (int i = 0; i < flipCount; i++) {
                    char tmp = digits[min + i];
                    digits[min + i] = digits[max - i];
                    digits[max - i] = tmp;
                }
                try {
                    return Integer.parseInt(String.valueOf(digits));
                } catch (NumberFormatException ignored) {
                    return 0;
                }
            }
        },
    }

}
