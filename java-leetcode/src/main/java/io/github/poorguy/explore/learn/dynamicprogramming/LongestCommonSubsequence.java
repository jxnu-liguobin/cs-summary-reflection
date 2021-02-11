/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.dynamicprogramming;

class LongestCommonSubsequence {
    // text1.length()>0 && text2.length()>0
    public int longestCommonSubsequence(String text1, String text2) {
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text2.length() + 1; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i < text1.length() + 1; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i < chars1.length + 1; i++) {
            for (int j = 1; j < chars2.length + 1; j++) {
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[text1.length()][text2.length()];
    }
}
