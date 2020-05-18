package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * 给定一个字符串s，你可以从中删除一些字符，使得剩下的串是一个回文串。如何删除才能使得回文串最长呢？ 输出需要删除的字符个数。 输入描述:
 * 输入数据有多组，每组包含一个字符串s，且保证:1<=s.length<=1000. 输出描述: 对于每组数据，输出一个整数，代表最少需要删除的字符个数。 示例1 输入abcda google
 * 输出 2 2
 */
public class Main14 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // 求字符串S1与S1反串的最大公共子序列
            // 删除的字符数目 = 原字符串的长度 - 最长公共子串的长度。
            String s1 = sc.next();
            String s2 = new StringBuilder(s1).reverse().toString();
            // 设MaxLen表示s1左边i个字符与s2左边j个字符的最长公共子串长度
            int[][] dp = new int[s1.length() + 1][s2.length() + 1];
            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    // 则子问题的解为MaxLen(strlen(s1),strlen(s2));
                    // 若s1第i个字符与s2第j个字符相匹配，则 return 1+MaxLen(i-1,j-1);
                    // 否则：return max(MaxLen(i-1,j),MaxLen(i,j-1))
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            System.out.println(s1.length() - dp[s1.length()][s2.length()]);
        }
        sc.close();
    }
}
