/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * @author kongwiki@163.com
 * @since 2020/8/13上午9:21
 */
public class MultiplyStrings {
    /**
     * 类似与两字符串相加
     *
     * <p>直白思路: 双重循环, 之后将计算的结果addString()
     */
    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        // 标记当前处理几个位了
        int carry = 1;
        // 控制进位数字
        int flag = 0;
        String res = "0";
        for (int i = num1.length() - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            int judge = 1;
            while (judge < carry) {
                sb.append("0");
                judge++;
            }
            int a = (num1.charAt(i) - '0');
            for (int j = num2.length() - 1; j >= 0; j--) {
                int b = (num2.charAt(j) - '0');
                int temp = a * b + flag;
                sb.append(temp % 10);
                flag = temp / 10;
            }
            if (flag > 0) {
                sb.append(flag);
            }
            flag = 0;
            res = addStrings(sb.reverse().toString(), res);
            carry++;
        }
        return res;
    }

    private static String addStrings(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0) {
            int a = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int b = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int temp = a + b + carry;
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
}
