/* All Contributors (C) 2020 */
package io.github.dreamylost.dp;

/**
 * 分割整数构成字母字符串
 *
 * <p>91. Decode Ways (Medium)
 *
 * <p>题目描述：Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 *
 * <p>1->A,2->B,12->L
 *
 * <p>字母只有26个
 *
 * @author 梦境迷离.
 * @time 2018年6月21日
 * @version v1.0
 */
public class Leetcode_91_Dp {

    public static void main(String[] args) {
        String n = "12";
        int ret = Leetcode_91_Dp.numDecodings(n);
        System.out.println(ret);
    }

    public static int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1; // 0开头的字符串，or非0开头的字符串
        for (int i = 2; i <= n; i++) {
            // 数字的每一位都是一个对应于字母表的字母【数字最大0~9，0除外，无对应字母】
            int one = Integer.valueOf(s.substring(i - 1, i));
            if (one != 0) {
                dp[i] += dp[i - 1];
            }

            if (s.charAt(i - 2) == '0') { // 0102这种只有一种可能，就是1，2->A,B,所以不需要进行下一步了
                continue;
            }

            int two = Integer.valueOf(s.substring(i - 2, i)); // 数字的每2位对应一个字母，2位数需要但小于26
            if (two <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }
}
