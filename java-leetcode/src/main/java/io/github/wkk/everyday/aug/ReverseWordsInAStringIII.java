/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * 题目：557. 反转字符串中的单词 III
 *
 * <p>给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
 *
 * <p>思路1：使用额外空间StringBuilder 每匹配出一个单词，然后进行reverse，最后进行拼接
 *
 * <p>思路2：转为数组， 然后匹配到一个单词进行交换
 *
 * @author kongwiki@163.com
 * @since 2020/8/30 9:48 上午
 */
public class ReverseWordsInAStringIII {
    public static String reverseWords(String s) {
        String res = "";
        if (s == null || s.length() == 0) {
            return res;
        }
        StringBuilder sb = new StringBuilder();
        // 双指针法
        int high = 0;
        while (high < s.length()) {
            if (s.charAt(high) != ' ') {
                sb.append(s.charAt(high));
                high++;
            } else {
                res += sb.reverse().toString() + " ";
                sb = new StringBuilder();
                high++;
            }
        }
        res += sb.reverse().toString();
        return res;
    }

    public static String reverseWordsII(String input) {
        String res = "";
        if (input == null || input.length() == 0) {
            return res;
        }
        char[] chars = input.toCharArray();
        int high = 0;
        while (high < chars.length) {
            int low = high;
            while (high < chars.length && chars[high] != ' ') {
                high++;
            }
            swap(chars, low, high - 1);
            high++;
        }
        return new String(chars);
    }

    public static void swap(char[] ch, int begin, int end) {
        while (begin < end) {
            char tmp = ch[begin];
            ch[begin] = ch[end];
            ch[end] = tmp;
            begin++;
            end--;
        }
    }

    public static void main(String[] args) {
        String a = "Let's take LeetCode contest";
        System.out.println(a.length());
        String s = reverseWordsII(a);
        System.out.println(s);
    }
}
