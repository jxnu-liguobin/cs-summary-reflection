package io.github.dreamylost;

/**
 * 删除两个字符串的字符使它们相等
 *
 * <p>583. Delete Operation for Two Strings (Medium)
 *
 * <p>Input: "sea", "eat" Output: 2 Explanation: You need one step to make "sea" to "ea" and another
 * step to make "eat" to "ea".
 *
 * @author 梦境迷离.
 * @time 2018年6月18日
 * @version v1.0
 */
public class Leetcode_583_LCS {

    /** 可以转换为求两个字符串的最长公共子序列问题。 */
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    continue;
                }
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 第i位结果依赖于第i-1位的结果值，所以从1开始
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 相当，同时后移一位，否则，要么i移动，要么j移动
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return m + n - 2 * dp[m][n]; // 最长公共子序列长度
    }
}
