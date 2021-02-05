/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.dynamicprogramming;

class MinCostClimbingStairs {
    // cost.length>=2
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 3];
        dp[0] = 0;
        dp[1] = 0;
        System.arraycopy(cost, 0, dp, 2, cost.length);
        dp[dp.length - 1] = 0;

        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + dp[i];
        }

        return dp[dp.length - 1];
    }
}
