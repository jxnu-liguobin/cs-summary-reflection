/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/**
 * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务， 就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
 * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
 */
public class T43 {

    public String LeftRotateString(String str, int n) {
        if (str.length() == 0) {
            return str;
        }
        for (int i = 0; i < n; i++) {
            char c = str.charAt(0);
            str = str.substring(1).concat(String.valueOf(c));
        }
        return str;
    }

    public String LeftRotateString2(String str, int n) {
        if (str == null || str.length() == 0) return "";

        char[] chars = str.toCharArray();
        if (chars.length < n) {
            return "";
        }
        reverse(chars, 0, n - 1);
        reverse(chars, n, chars.length - 1);
        reverse(chars, 0, chars.length - 1);

        return new String(chars);
    }

    public void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }
}
