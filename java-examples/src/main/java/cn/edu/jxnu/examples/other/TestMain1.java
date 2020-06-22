/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.other;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @time 2018-09-09
 */
public class TestMain1 {

    public static String reverseString(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    /**
     * 最大对称子串
     *
     * @param str
     * @return
     */
    public static String longestPalindrome(String str) {
        String max = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = str.length(); (j >= i) & (j - i >= max.length()); j--) {
                count++;
                String substr = str.substring(i, j);
                int middle = 0;
                if (substr.length() % 2 == 0) {
                    middle = substr.length() / 2;
                } else {
                    middle = substr.length() / 2 + 1;
                }
                if (substr.substring(0, substr.length() / 2)
                        .equals(reverseString(substr.substring(middle)))) {
                    if (max.length() < substr.length()) {
                        max = substr;
                    }
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        System.out.println(longestPalindrome(str));
    }
}
