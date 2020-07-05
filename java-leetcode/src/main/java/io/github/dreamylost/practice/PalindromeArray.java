/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * 3、回文数组 【题目描述】对于一个给定的正整数组成的数组 a[] ，如果将 a 倒序后数字的排列与 a 完全相同，我们 称这个数组为“回文”的。 例如，[1, 2, 3, 2, 1]
 * 的倒序是他自己，所以是一个回文的数组；而 [1, 2, 3, 1, 2] 的倒序是 [2, 1, 3, 2, 1] ，所以不是一个回文的数组。
 * 对于任意一个正整数数组，如果我们向其中某些特定的位置插入一些正整数，那么我们总是能构造出一个 回文的数组。
 * 输入一个正整数组成的数组，要求你插入一些数字，使其变为回文的数组，且数组中所有数字的和尽可能 小。输出这个插入后数组中元素的和。 例如，对于数组 [1,2, 3, 1, 2] 我们可以插入两个
 * 1 将其变为回文的数组 [1, 2, 1, 3, 1, 2, 1] ， 这种变换方式数组的总和最小，为 11 ，所以输出为 11 。 输入描述: 输入数据由两行组成： 第一行包含一个正整数 L
 * ，表示数组 a 的长度。 第二行包含 L 个正整数，表示 数组 a 。对于 40% 的数据： 1 < L <= 100 达成条件时需要插入的数字数量不多于 2 个。 对于 100% 的数据： 1
 * < L <= 1,000 0 < a[i] <= 1,000,000 达成条件时需要插入的数字数量没有限制。
 *
 * <p>输出描述: 输出一个整数，表示通过插入若干个正整数使数组 a 回文后，数组a 的数字和的最小值。 输入示例 8 51 23 52 97 97 76 23 51 输出示例 598
 *
 * @author 梦境迷离.
 * @time 2018年8月3日
 * @version v1.0
 */
public class PalindromeArray {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int len = sc.nextInt();
            int[] nums = new int[len];
            for (int i = 0; i < len; i++) {
                nums[i] = sc.nextInt();
            }
            Integer[][] dp = new Integer[len][len];
            int res = palindromeArrayHelper(nums, dp, 0, len - 1);
            System.out.println(res);
        }
        sc.close();
    }

    public static int palindromeArrayHelper(int[] nums, Integer[][] dp, int i, int j) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return nums[i];
        }
        if (dp[i][j] != null) {
            return dp[i][j];
        }
        if (nums[i] == nums[j]) {
            dp[i][j] = 2 * nums[i] + palindromeArrayHelper(nums, dp, i + 1, j - 1);
        } else {
            dp[i][j] =
                    Math.min(
                            2 * nums[i] + palindromeArrayHelper(nums, dp, i + 1, j),
                            2 * nums[j] + palindromeArrayHelper(nums, dp, i, j - 1));
        }
        return dp[i][j];
    }
}
