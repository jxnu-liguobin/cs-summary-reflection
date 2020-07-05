/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 两个字符串包含的字符是否完全相同
 *
 * <p>242. Valid Anagram (Easy)
 */
public class Leetcode_242_String {

    public static void main(String[] arge) {
        String a = "abcd";
        String b = "bcda";
        boolean flag = Leetcode_242_String.isAnagram(a, b);
        System.out.println(flag);
    }

    public static boolean isAnagram(String a, String b) {

        int[] cnt = new int[26];
        char[] ca = a.toCharArray();
        char[] cb = b.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            cnt[ca[i] - 'a']++;
        }

        for (int i = 0; i < cb.length; i++) {
            cnt[cb[i] - 'a']--;
        }

        for (int c : cnt) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }
}
