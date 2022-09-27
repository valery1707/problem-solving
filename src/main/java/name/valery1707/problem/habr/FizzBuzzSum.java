package name.valery1707.problem.habr;

import java.io.PrintStream;

/**
 * Написать код, который выводит числа от 0 до 1000, которые делятся на 3, но не делятся на 5, и сумма цифр в которых меньше десяти.
 *
 * @see <a href="https://habr.com/ru/company/skillbox/blog/689226/">Задача</a>
 */
@SuppressWarnings("scwloggingslf4j_SLF4JLoggingSystem.out")
public class FizzBuzzSum {

    public void naive(PrintStream out, int min, int max, int lim) {
        for (int i = min; i <= max; i++) {
            var div3 = i % 3 == 0;
            var div5 = i % 5 == 0;
            int sum = sumDigits(i);
            if (div3 && !div5 && sum < lim) {
                out.println(i);
            }
        }
    }

    public void simple(PrintStream out, int min, int max, int lim) {
        for (int i = min; i <= max; i++) {
            if (i % 3 == 0 && i % 5 != 0 && sumDigits(i) < lim) {
                out.println(i);
            }
        }
    }

    private int sumDigits(int value) {
        int sum = 0;
        while (value != 0) {
            sum += value % 10;
            value = value / 10;
        }
        return sum;
    }

    public void summationDancing(PrintStream out, int min, int max, int lim) {
        int sumCount = String.valueOf(max).length() + 1;//Можно оптимизировать, но за счёт снижения читаемости
        int[] sum = new int[sumCount--];//В последнем знаке мы точно не превысим 10-ку
        for (int i = min; i <= max; i++) {
            if (i % 3 == 0 && i % 5 != 0 && sumDigits(sum) < lim) {
                out.println(i);
            }
            sum[0]++;
            for (int s = 0; s < sumCount; s++) {
                if (sum[s] == 10) {
                    sum[s] = 0;
                    sum[s + 1]++;
                }
            }
        }
    }

    private int sumDigits(int[] sum) {
        int res = 0;
        for (int i : sum) {
            res += i;
        }
        return res;
    }


}
