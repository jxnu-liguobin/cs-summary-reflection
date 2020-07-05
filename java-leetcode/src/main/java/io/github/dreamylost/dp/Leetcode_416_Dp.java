/* All Contributors (C) 2020 */
package io.github.dreamylost.dp;

/**
 * 划分数组为和相等的两部分
 *
 * <p>416. Partition Equal Subset Sum (Medium)
 *
 * <p>Input: [1, 5, 11, 5]
 *
 * <p>Output: true
 *
 * <p>Explanation: The array can be partitioned as [1, 5, 5] and [11]. 可以看成一个背包大小为 sum/2 的 0-1 背包问题。
 *
 * @author 梦境迷离.
 * @time 2018年6月12日
 * @version v1.0
 */
public class Leetcode_416_Dp {

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        } // 必须是偶数才可以划分
        int w = sum / 2; // 相当于背包的容量
        boolean[] dp = new boolean[w + 1];
        dp[0] = true;
        for (int num : nums) { // 0-1 背包一个物品只能用一次
            for (int i = w; i >= 0; i--) { // 从后往前，先计算 dp[i] 再计算 dp[i-num]
                if (num <= i) { // 物品小于容量
                    dp[i] = dp[i] || dp[i - num];
                }
            }
        }

        return dp[w];
    }
}
