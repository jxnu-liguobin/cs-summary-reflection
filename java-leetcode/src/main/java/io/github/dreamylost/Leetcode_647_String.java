/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 回文子字符串
 *
 * <p>647. Palindromic Substrings (Medium)
 *
 * <p>Input: "aaa" Output: 6 Explanation: Six palindromic strings: "a", "a", "a","aa", "aa", "aaa".
 * 从字符串的某一位开始，尝试着去扩展子字符串。
 *
 * @author 梦境迷离.
 * @time 2018年7月7日
 * @version v1.0
 */
public class Leetcode_647_String {

    private static int cnt = 0;

    /**
     * aaa a,a,
     *
     * <p>a[3个] aa,aa[2个] aaa[3个]
     *
     * <p>对第一个字符(寻找,以该字符本身为中间位向旁边拓展) 对第二个字符(寻找) 对第三个字符(寻找)
     */
    public static int countSubstrings(String s) {
        for (int i = 0; i < s.length(); i++) {
            extendSubstrings(s, i, i); // 奇数，1-3[0-0,1-1,2-2]=3
            extendSubstrings(s, i, i + 1); // 偶数长度，2[0-1,1-2,2-3]=1+2
        }

        return cnt;
    }

    private static void extendSubstrings(String s, int start, int end) {
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
            cnt++;
        }
    }
}
