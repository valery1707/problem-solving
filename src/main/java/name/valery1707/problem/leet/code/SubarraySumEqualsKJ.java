package name.valery1707.problem.leet.code;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>560. Subarray Sum Equals K</h1>
 * <p>
 * Given an array of integers {@code nums} and an integer {@code k}, return <i>the total number of subarrays whose sum equals to {@code k}</i>.
 * <p>
 * A subarray is a contiguous <b>non-empty</b> sequence of elements within an array.
 * <h3>Constraints</h3>
 * <ul>
 * <li>{@code 1 <= nums.length <= 2 * 10^4}</li>
 * <li>{@code -1000 <= nums[i] <= 1000}</li>
 * <li>{@code -10^7 <= k <= 10^7}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/subarray-sum-equals-k/">560. Subarray Sum Equals K</a>
 */
public interface SubarraySumEqualsKJ {

    int subarraySum(int[] nums, int k);

    enum Implementation implements SubarraySumEqualsKJ {
        /**
         * 92 / 92 test cases passed, but took too long.
         */
        naive {
            @Override
            public int subarraySum(int[] nums, int k) {
                int found = 0;
                for (int i = 0; i < nums.length; i++) {
                    int sum = 0;
                    for (int j = i; j < nums.length; j++) {
                        sum += nums[j];
                        if (sum == k) {
                            found++;
                        }
                    }
                }
                return found;
            }
        },
        prefixSum {
            @Override
            public int subarraySum(int[] nums, int k) {
                int[] sums = new int[nums.length + 1];
                for (int i = 0; i < nums.length; i++) {
                    sums[i + 1] = sums[i] + nums[i];
                }

                int found = 0;
                for (int i = 0; i < nums.length; i++) {
                    for (int j = i; j < nums.length; j++) {
                        if (sums[j + 1] - sums[i] == k) {
                            found++;
                        }
                    }
                }

                return found;
            }
        },
        prefixSumCached {
            @Override
            public int subarraySum(int[] nums, int k) {
                int[] sums = new int[nums.length + 1];
                for (int i = 0, sum = 0; i < nums.length; i++) {
                    sum += nums[i];
                    sums[i + 1] = sum;
                }

                int found = 0;
                for (int i = 0; i < nums.length; i++) {
                    for (int j = i, sum = sums[i]; j < nums.length; j++) {
                        if (sums[j + 1] - sum == k) {
                            found++;
                        }
                    }
                }

                return found;
            }
        },
        /**
         * Runtime: {@code 53 ms}, faster than {@code 66.57%} of Java online submissions for Subarray Sum Equals K.
         * Memory Usage: {@code 65.3 MB}, less than {@code 69.62%} of Java online submissions for Subarray Sum Equals K.
         */
        sumFrequency {
            @Override
            public int subarraySum(int[] nums, int k) {
                int found = 0;
                Map<Integer, Integer> frequency = new HashMap<>();
                frequency.put(0, 1);//zero-sum of 1 frequency
                for (int i = 0, sum = 0; i < nums.length; i++) {
                    //calculating the running sum or the prefix sum
                    sum += nums[i];
                    //we are searching for any sum-k is there or not and what is its frequency
                    //what's the frequency (sum-k) value and adding that to count, as we find the range in which the subarray lies whose sum will be k
                    found += frequency.getOrDefault(sum - k, 0);
                    frequency.put(sum, frequency.getOrDefault(sum, 0) + 1);
                }
                return found;
            }
        },
    }

}
