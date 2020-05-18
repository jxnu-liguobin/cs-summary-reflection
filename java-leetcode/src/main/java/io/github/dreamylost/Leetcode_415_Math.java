package io.github.dreamylost;

/**
 * 字符串加法
 *
 * <p>415. Add Strings (Easy)
 *
 * <p>字符串的值为非负整数。
 *
 * @author 梦境迷离.
 * @time 2018年6月23日
 * @version v1.0
 */
public class Leetcode_415_Math {

    public String addStrings(String num1, String num2) {
        StringBuilder str = new StringBuilder();
        int carry = 0, i = num1.length() - 1, j = num2.length() - 1;
        while (carry == 1 || i >= 0 || j >= 0) {
            int x = i < 0 ? 0 : num1.charAt(i--) - '0';
            int y = j < 0 ? 0 : num2.charAt(j--) - '0';
            str.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        return str.reverse().toString();
    }
}
