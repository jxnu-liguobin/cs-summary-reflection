package io.github.dreamylost;

/**
 * 567. 字符串的排列
 * <p>
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 * <p>
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_567_Double_Pointer {


    /**
     * 滑动窗口
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) return false;


        int[] count = new int[26];
        for (int i = 0; i < len1; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        //s1,s2中的各个字母次数相等，在len1长度
        if (allZero(count))
            return true;
        for (int i = len1; i < len2; i++) {
            count[s2.charAt(i) - 'a']--;//基于len1开始，右移动--
            count[s2.charAt(i - len1) - 'a']++;//左移++
            if (allZero(count))
                return true;
        }
        return false;
    }

    private boolean allZero(int[] count) {
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
}
