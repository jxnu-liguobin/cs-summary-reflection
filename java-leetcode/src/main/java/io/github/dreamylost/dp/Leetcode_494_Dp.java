/* All Contributors (C) 2020 */
package io.github.dreamylost.dp;

import java.util.Arrays;

/**
 * 改变一组数的正负号使得它们的和为一给定数
 *
 * <p>494. Target Sum (Medium)
 *
 * <p>Input: nums is [1, 1, 1, 1, 1], S is 3. Output: 5 Explanation:
 *
 * <p>-1+1+1+1+1 = 3 +1-1+1+1+1 = 3 +1+1-1+1+1 = 3 +1+1+1-1+1 = 3 +1+1+1+1-1 = 3
 *
 * <p>There are 5 ways to assign symbols to make the sum of nums be target 3. 该问题可以转换为 Subset Sum
 * 问题，从而使用 0-1 背包的方法来求解。
 *
 * <p>可以将这组数看成两部分，P 和 N，其中 P 使用正号，N 使用负号，有以下推导：
 *
 * <p>sum(P) - sum(N) = target sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P)+ sum(N) 2 *
 * sum(P) = target + sum(nums) 因此只要找到一个子集，令它们都取正号，并且和等于 (target + sum(nums))/2，就证明存在解。
 *
 * @author 梦境迷离.
 * @time 2018年6月16日
 * @version v1.0
 */
public class Leetcode_494_Dp {

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 1, 1};
        int ret = Leetcode_494_Dp.findTargetSumWays2(arr, 3);
        System.out.println(ret);
    }

    /**
     * 使用背包问题思路解决
     *
     * @param nums
     * @param s
     * @return
     */
    public static int findTargetSumWays(int[] nums, int s) {
        int sum = computeArraySum(nums);
        if (sum < s || (sum + s) % 2 == 1) { // 不可能的情况
            return 0;
        }
        int w = (sum + s) / 2; // 转化为容量为(sum+s)/2的背包问题
        int[] dp = new int[w + 1];
        dp[0] = 1;
        Arrays.sort(nums);
        for (int num : nums) { // 元素，不可重复
            for (int i = w; i >= num; i--) {
                dp[i] = dp[i] + dp[i - num]; // 倒着求
            }
        }
        return dp[w];
    }

    private static int computeArraySum(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }

    /** DFS 解法： */
    public static int findTargetSumWays2(int[] nums, int S) {
        return findTargetSumWays(nums, 0, S);
    }

    private static int findTargetSumWays(int[] nums, int start, int S) {
        if (start == nums.length) {
            return S == 0 ? 1 : 0;
        }
        // 同时使用+和-
        return findTargetSumWays(nums, start + 1, S + nums[start])
                + findTargetSumWays(nums, start + 1, S - nums[start]);
    }
}
