package io.github.dreamylost.dp;

/**
 * 01 字符构成最多的字符串
 *
 * <p>474. Ones and Zeroes (Medium)
 *
 * <p>Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3 Output: 4
 *
 * <p>Explanation: There are totally 4 strings can be formed by the using of 5 0s and 3 1s, which
 * are "10","0001","1","0"
 *
 * @author 梦境迷离.
 * @time 2018年6月16日
 * @version v1.0
 */
public class Leetcode_474_Dp {

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) { // 每个字符串只能用一次
            int ones = 0, zeros = 0;
            for (char c : s.toCharArray()) {
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            // 这是一个多维费用的 0-1 背包问题，有两个背包大小，0 的数量和 1 的数量。
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }
}
