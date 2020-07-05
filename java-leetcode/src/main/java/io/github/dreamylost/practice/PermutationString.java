/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.ArrayList;

/**
 * @description 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
 *     例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * @note 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
 * @author Mr.Li
 */
public class PermutationString {

    public static void main(String[] args) {
        String str = "ABCDE";
        java.util.List<String> list = new PermutationString().permutation(str);
        for (String string : list) {
            System.out.println(string);
        }
    }

    /** ********************************************************** */
    /**
     * @description 递归算法
     * @param str
     * @return
     */
    public ArrayList<String> permutation(String str) {
        ArrayList<String> res = new ArrayList<String>();
        if (str != null && str.length() > 0) {
            PermutationUtils2(str.toCharArray(), 0, res);
            java.util.Collections.sort(res);
        }
        return res;
    }

    private static void swapChar(char[] cs, int i, int j) {
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }

    private static void PermutationUtils2(char[] str, int i, ArrayList<String> list) {
        if (i == str.length - 1) { // 解空间的一个叶节点
            list.add(String.valueOf(str)); // 找到一个解
        }
        for (int j = i; j < str.length; ++j) {
            if (j == i || str[j] != str[i]) {
                PermutationString.swapChar(str, i, j);
                PermutationUtils2(str, i + 1, list);
                PermutationString.swapChar(str, i, j); // 复位
            }
        }
    }

    /**
     * @description 数组逆转
     * @param arr
     * @param begin
     * @param end
     * @return
     */
    public String reverseCharArray(String arr, int begin, int end) {
        char[] t = arr.toCharArray();
        while (end > begin) {
            char temp = t[begin];
            t[begin] = t[end];
            t[end] = temp;
            begin++;
            end--;
        }
        return String.valueOf(t);
    }

    /**
     * @description 数组循环移位
     * @param str
     */
    public static void moveString(String str) {
        char[] s = str.toCharArray();
        int len = s.length;
        for (int i = 0; i < len; i++) {
            char temp = s[0];
            for (int j = 0; j < len - 1; j++) {
                s[j] = s[j + 1];
            }
            s[len - 1] = temp;
            // 注意不要改变原数组
        }
    }
}
