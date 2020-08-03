/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * @author kongwiki@163.com
 * @since 2020/8/3上午9:18
 */
public class AddStrings {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        // 万能步骤
        // 可用于整数的相加, 链表的相加, 字符串的相加
        while (i >= 0 || j >= 0) {
            int a = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int b = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int temp = (a + b + carry);
            sb.append(temp % 10);
            carry = temp / 10;
            i--;
            j--;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {}
}
