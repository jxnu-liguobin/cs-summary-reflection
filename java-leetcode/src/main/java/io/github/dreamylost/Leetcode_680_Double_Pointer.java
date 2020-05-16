package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 字符串回文数（可删除一个字符）
 * @time 2018年4月7日
 */
public class Leetcode_680_Double_Pointer {
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            // 不是对称
            if (s.charAt(i) != s.charAt(j)) {
                // 删除后面一个或前面一个
                return isPalindrome(s, i, j - 1) || isPalindrome(s, i + 1, j);
            }
            // 对称，直接向前推进
            i++;
            j--;
        }
        return true;
    }

    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            // 假设删除之后仍不相等则直接返回fasle
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
