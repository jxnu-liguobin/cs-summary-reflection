/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 计算一组字符集合可以组成的回文字符串的最大长度
 *
 * <p>409. Longest Palindrome (Easy)
 *
 * <p>Input : "abccccdd" Output : 7 Explanation : One longest palindrome that can be built is
 * "dccaccd", whose length is 7. 使用长度为 256 的整型数组来统计每个字符出现的个数，每个字符有偶数个可以用来构成回文字符串。
 *
 * <p>因为回文字符串最中间的那个字符可以单独出现，所以如果有单独的字符就把它放到最中间。
 *
 * @author 梦境迷离.
 * @time 2018年7月5日
 * @version v1.0
 */
public class Leetcode_409_String {

    public static void main(String[] args) {
        int ret = Leetcode_409_String.longestPalindrome("abccccdd");
        System.out.println(ret);
    }

    public static int longestPalindrome(String s) {
        int[] cnts = new int[256];
        for (char c : s.toCharArray()) {
            cnts[c]++;
        }
        int palindrome = 0;
        for (int cnt : cnts) {
            palindrome += (cnt / 2) * 2;
        }
        if (palindrome < s.length()) {
            palindrome++; // 这个条件下 s 中一定有单个未使用的字符存在，可以把这个字符放到回文的最中间
        }
        return palindrome;
    }
}
