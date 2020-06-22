/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 题目描述 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0 输入描述:
 *     输入一个字符串,包括数字字母符号,可以为空 输出描述: 如果是合法的数值表达则返回该数字，否则返回0 示例1 输入 +2147483647 1a33 输出 2147483647 0
 * @author Mr.Li
 */
public class StrToInt {

    public static void main(String[] args) {
        System.out.println(new StrToInt().strToInt("-2147483648"));
    }

    public int strToInt(String str) {
        if (str.equals("") || str.length() == 0) return 0;
        char[] a = str.toCharArray();
        int fuhao = 0;
        if (a[0] == '-') fuhao = 1;
        int sum = 0;
        for (int i = fuhao; i < a.length; i++) {
            if (a[i] == '+') continue;
            if (a[i] < 48 || a[i] > 57) return 0;
            sum = sum * 10 + a[i] - 48;
        }
        return fuhao == 0 ? sum : sum * -1;
    }
}
