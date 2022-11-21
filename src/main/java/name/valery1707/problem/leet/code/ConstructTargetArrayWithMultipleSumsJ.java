package name.valery1707.problem.leet.code;

import java.util.Arrays;

/**
 * You are given an array {@code target} of n integers.
 * From a starting array {@code arr} consisting of {@code n} 1's, you may perform the following procedure :
 * <ul>
 * <li>let {@code x} be the sum of all elements currently in your array.</li>
 * <li>choose index {@code i}, such that {@code 0 <= i < n} and set the value of {@code arr} at index {@code i} to {@code x}.</li>
 * <li>You may repeat this procedure as many times as needed.</li>
 * </ul>
 * Return {@code true} if it is possible to construct the {@code target} array from {@code arr}, otherwise, return {@code false}.
 * <p>
 * Constraints:
 * <ul>
 * <li>{@code n == target.length}</li>
 * <li>{@code 1 <= n <= 5 * 10^4}</li>
 * <li>{@code 1 <= target[i] <= 10^9}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/construct-target-array-with-multiple-sums/">1354. Construct Target Array With Multiple Sums</a>
 */
public interface ConstructTargetArrayWithMultipleSumsJ {

    boolean isPossible(int[] target);

    enum Implementation implements ConstructTargetArrayWithMultipleSumsJ {
        /**
         * Реализация без создания объектов, но требуется сортировка на каждом шаге.
         * <p>
         * Результат:
         * <ol>
         * <li>Runtime: {@code 12 ms}, faster than {@code 79.80%} of Java online submissions for Construct Target Array With Multiple Sums.</li>
         * <li>Memory Usage: {@code 47.7 MB}, less than {@code 100.00%} of Java online submissions for Construct Target Array With Multiple Sums.</li>
         * </ol>
         */
        arrayManipulation {
            @Override
            public boolean isPossible(int[] target) {
                var last = target.length - 1;
                Arrays.sort(target);

                if (target.length == 1) {
                    //Если элемент всего один, то корректность зависит от его значения
                    return target[last] == 1;
                }

                try {
                    //Быстрый вариант через работу с суммой
                    long sum = 0;
                    for (int i = 0; i <= last; i++) {
                        sum = Math.addExact(sum, target[i]);
                    }
                    while (canReduce(target)) {
                        int curr = target[last];
                        if ((sum - curr) > curr) {
                            //Сумма остальных элементов даёт значение больше максимального, а значит из них нельзя было составить рассматриваемое значение
                            return false;
                        }
                        var prev = curr % (sum - curr);
                        sum = sum - curr + prev;
                        insertIntoSorted(target, Math.toIntExact(prev));
                    }
                } catch (ArithmeticException ignored) {
                    //Медленный вариант если встречаем переполнение
                    while (canReduce(target)) {
                        var prev = target[last];
                        for (int i = 0; i < last; i++) {
                            prev -= target[i];
                        }
                        insertIntoSorted(target, prev);
                    }
                }

                return target[0] == 1;
            }

            private static boolean canReduce(int[] target) {
                //(максимальный элемент не равен минимальному) И (не случай `[1, X]` - его можно не сворачивать, а значит считать корректным)
                return (target[0] >= 1 && target[target.length - 1] != target[0]) && !(target.length == 2 && target[0] == 1);
            }

            /**
             * Так как мы точно знаем что массив отсортирован и знаем как именно он отсортирован, то можем выполнить эффективную вставку, работающую со сложностью
             * {@code O(N)} вместо сложности {@code O(N log(N))} у {@link Arrays#sort(int[])}.
             */
            private static void insertIntoSorted(int[] target, int value) {
                for (int i = 0; i < target.length; i++) {
                    if (target[i] > value) {
                        //Начинаем вставку со смещением
                        int old = target[i];
                        target[i] = value;
                        value = old;
                    }
                }
            }
        },
    }

}
