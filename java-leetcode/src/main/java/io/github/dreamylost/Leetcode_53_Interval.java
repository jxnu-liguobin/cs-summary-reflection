/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 子数组最大的和
 *
 * <p>53. Maximum Subarray (Easy)
 *
 * <p>For example, given the array [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has
 * the largest sum = 6.
 *
 * <p>子数组应该是连续的，只需要求和，不需要具体位置，数组可能包含正负整数，0
 *
 * @author 梦境迷离.
 * @time 2018年6月18日
 * @version v1.0
 */
public class Leetcode_53_Interval {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int preSum = nums[0]; // 保存前一个元素的，因为正负很重要
        int maxSum = preSum; // 当前临时最大值，最后的值就是最大值
        for (int i = 1; i < nums.length; i++) {
            // 第一个为正整数，则加起来，否则从第二个元素开始
            if (preSum > 0) preSum = preSum + nums[i];
            else preSum = nums[i];

            maxSum = Math.max(maxSum, preSum);
        }
        return maxSum;
    }
}
