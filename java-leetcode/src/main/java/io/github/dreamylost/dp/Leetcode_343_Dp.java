package io.github.dreamylost.dp;

/**
 * 分割整数的最大乘积
 *
 * <p>343. Integer Break (Medim)
 *
 * <p>题目描述：For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).
 *
 * @author 梦境迷离.
 * @time 2018年6月19日
 * @version v1.0
 */
public class Leetcode_343_Dp {

    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) { // 对于指定的i,j的取值在（1~i-1）
            for (int j = 1; j <= i - 1; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j * (i - j)));
            }
        }
        return dp[n];
    }
}
