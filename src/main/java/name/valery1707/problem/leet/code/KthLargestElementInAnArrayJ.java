package name.valery1707.problem.leet.code;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the {@code k-th} largest element in the array.
 * <p>
 * Note that it is the {@code k-th} largest element in the sorted order, not the {@code k-th} distinct element.
 * <p>
 * You must solve it in {@code O(n)} time complexity.
 * <p>
 * Constraints:
 * <ul>
 * <li>{@code 1 <= k <= nums.length <= 10^5}</li>
 * <li>{@code -10^4 <= nums[i] <= 10^4}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/kth-largest-element-in-an-array/">215. Kth Largest Element in an Array</a>
 */
public interface KthLargestElementInAnArrayJ {

    int findKthLargest(int[] nums, int k);

    enum Implementation implements KthLargestElementInAnArrayJ {
        /**
         * Решение "в лоб": используем стандартные механизмы и получаем довольно быстрый, короткий и понятный код.
         * <p>
         * Результат:
         * <ol>
         * <li>Runtime: {@code 5 ms}, faster than {@code 69.12%} of Java online submissions for Kth Largest Element in an Array.</li>
         * <li>Memory Usage: {@code 45.1 MB}, less than {@code 25.97%} of Java online submissions for Kth Largest Element in an Array.</li>
         * </ol>
         */
        naive {
            @Override
            public int findKthLargest(int[] nums, int k) {
                Arrays.sort(nums);
                return nums[nums.length - k];
            }
        },
    }

}
