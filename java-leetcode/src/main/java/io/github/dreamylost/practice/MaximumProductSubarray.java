/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.List;

/**
 * 找出一个序列中乘积最大的连续子序列（至少包含一个数）。
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
public class MaximumProductSubarray {

    public int maxProduct(List<Integer> nums) {
        int[] max = new int[nums.size()];
        int[] min = new int[nums.size()];

        min[0] = max[0] = nums.get(0);
        int result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            min[i] = max[i] = nums.get(i);
            if (nums.get(i) > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums.get(i));
                min[i] = Math.min(min[i], min[i - 1] * nums.get(i));
            } else if (nums.get(i) < 0) {
                max[i] = Math.max(max[i], min[i - 1] * nums.get(i));
                min[i] = Math.min(min[i], max[i - 1] * nums.get(i));
            }

            result = Math.max(result, max[i]);
        }

        return result;
    }

    public int maxProduct2(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];

        min[0] = max[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = max[i] = nums[i];
            if (nums[i] > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums[i]);
                min[i] = Math.min(min[i], min[i - 1] * nums[i]);
            } else if (nums[i] < 0) {
                max[i] = Math.max(max[i], min[i - 1] * nums[i]);
                min[i] = Math.min(min[i], max[i - 1] * nums[i]);
            }

            result = Math.max(result, max[i]);
        }

        return result;
    }

    public int maxProduct3(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];

        min[0] = max[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = max[i] = nums[i];
            if (nums[i] > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums[i]);
                min[i] = Math.min(min[i], min[i - 1] * nums[i]);
            } else if (nums[i] < 0) {
                max[i] = Math.max(max[i], min[i - 1] * nums[i]);
                min[i] = Math.min(min[i], max[i - 1] * nums[i]);
            }

            result = Math.max(result, max[i]);
        }

        return result;
    }
}
