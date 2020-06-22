/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 编辑距离
 *
 * <p>72. Edit Distance (Hard)
 *
 * <p>Example 1:
 *
 * <p>Input: word1 = "horse", word2 = "ros" Output: 3 Explanation: horse -> rorse (replace 'h' with
 * 'r') rorse -> rose (remove 'r') rose -> ros (remove 'e') Example 2:
 *
 * <p>Input: word1 = "intention", word2 = "execution" Output: 5 Explanation: intention -> inention
 * (remove 't') inention -> enention (replace 'i' with 'e') enention -> exention (replace 'n' with
 * 'x') exention -> exection (replace 'n' with 'c') exection -> execution (insert 'u')
 * 题目描述：修改一个字符串成为另一个字符串，使得修改次数最少。一次修改操作包括：插入一个字符、删除一个字符、替换一个字符。
 *
 * @author 梦境迷离.
 * @time 2018年6月19日
 * @version v1.0
 */
public class Leetcode_72_LCS {

    /** 把word1变成word2 */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
