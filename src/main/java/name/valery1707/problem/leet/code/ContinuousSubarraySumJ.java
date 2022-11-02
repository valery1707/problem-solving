package name.valery1707.problem.leet.code;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>523. Continuous Subarray Sum</h1>
 * <p>
 * Given an integer array {@code nums} and an integer {@code k}, return {@code true} if {@code nums} has a <b>good subarray</b> or {@code false} otherwise.
 * <p>
 * A <b>good subarray</b> is a subarray where:
 * <ul>
 * <li>its length is <b>at least two</b>, and</li>
 * <li>the sum of the elements of the subarray is a multiple of {@code k}</li>
 * </ul>
 * <p>
 * Note that:
 * <ul>
 * <li>A <b>subarray</b> is a contiguous part of the array</li>
 * <li>An integer {@code x} is a multiple of {@code k} if there exists an integer {@code n} such that {@code x = n * k}.
 * {@code 0} is always a multiple of {@code k}</li>
 * </ul>
 * <h3>Constraints</h3>
 * <ul>
 * <li>{@code 1 <= nums.length <= 2 * 10^5}</li>
 * <li>{@code 0 <= nums[i] <= 10^9}</li>
 * <li>{@code 0 <= sum(nums[i]) <= 2^31 - 1}</li>
 * <li>{@code 1 <= k <= 2^31 - 1}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/continuous-subarray-sum/">523. Continuous Subarray Sum</a>
 */
public interface ContinuousSubarraySumJ {

    boolean checkSubarraySum(int[] nums, int k);

    enum Implementation implements ContinuousSubarraySumJ {
        /**
         * Time Limit Exceeded
         */
        naive {
            @Override
            public boolean checkSubarraySum(int[] nums, int k) {
                for (int i = 0; i < nums.length; i++) {
                    int sum = nums[i];
                    for (int j = i + 1; j < nums.length; j++) {
                        sum += nums[j];
                        if (sum % k == 0) {
                            return true;
                        }
                    }
                }
                return false;
            }
        },
        /**
         * Runtime: {@code 59 ms}, faster than {@code 62.00%} of Java online submissions for Continuous Subarray Sum.
         * <p>
         * Memory Usage: {@code 106.3 MB}, less than {@code 74.38%} of Java online submissions for Continuous Subarray Sum.
         *
         * @see SubarraySumEqualsKJ.Implementation#sumFrequency
         */
        sumFrequency {
            @Override
            public boolean checkSubarraySum(int[] nums, int k) {
                //logic is: (sum2 - sum1)%k == 0 => sum2%k == sum1%k
                Set<Integer> seen = new HashSet<>();
                //also we're keeping and adding a prev sum, so we can check for sub-arrays with length >= 2
                int prev = 0;
                int curr = 0;
                for (int num : nums) {
                    curr = (curr + num) % k;
                    if (seen.contains(curr)) {
                        return true;
                    }
                    seen.add(prev);
                    prev = curr;
                }
                return false;
            }
        },
    }

}
