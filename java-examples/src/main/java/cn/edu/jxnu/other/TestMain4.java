/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.other;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @time 2018-09-17
 */
public class TestMain4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // String s = scanner.nextLine();
        int n = scanner.nextInt();
        int ret = utilFor2(n);
        System.out.println(ret);
        // String ret = util(s.trim().toCharArray(), s.length() - 1);
        // System.out.println(ret);

    }

    /**
     * 字符串反转
     *
     * @param str
     * @param length
     * @return
     */
    private static String util(char[] str, int length) {
        for (int i = 0; i <= length / 2; i++) {
            char temp = str[i];
            str[i] = str[length - i];
            str[length - i] = temp;
        }
        return new String(str).trim();
    }

    /**
     * 计算0~n之间含有的2的个数
     *
     * @param end
     * @return
     */
    private static int utilFor2(int end) {
        int count = 0; // 统计2出现的次数的变量count
        // 从开始的数字到结束的数字循环,一个一个数判断
        for (int i = 0; i <= end; i++) {
            int tmp = i; // 把i赋给一个临时变量tmp
            // 把这个数的每一位数字用tmp%10取出,判断其是否等于2
            while (tmp > 0) {
                int n = tmp % 10;
                if (n == 2) { // 如果这个数中有一位的数字等于2,count加1
                    count++;
                }
                tmp = tmp / 10;
            }
        }
        return count;
    }
}
