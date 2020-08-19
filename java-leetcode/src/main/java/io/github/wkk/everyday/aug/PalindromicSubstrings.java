/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * @author kongwiki@163.com
 * @since 2020/8/19上午8:50
 */
public class PalindromicSubstrings {
    /**
     * 基本思路: 双重循环 检测所有的结果
     *
     * <p>回文串的判定方法: 1.中心拓展法 2.Manacher算法
     */
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = s.length();
        // 循环检测是是否为回文子串
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                String temp = s.substring(i, j + 1);
                if (isPalindromic(temp, 0, temp.length() - 1)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isPalindromic(String s, int i, int j) {
        if (i > j) {
            return false;
        }
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
