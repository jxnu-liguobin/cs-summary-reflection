/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * Title: Main1.java
 *
 * <p>Description: 为了得到一个数的"相反数",我们将这个数的数字顺序颠倒,然后再加上原先的数得到"相反数"。
 * 例如,为了得到1325的"相反数",首先我们将该数的数字顺序颠倒,我们得到5231,之后再加上原先的数, 我们得到5231+1325=6556.如果颠倒之后的数字有前缀零,前缀零将会被忽略。
 * 例如n = 100, 颠倒之后是1.
 *
 * <p>输入描述: 输入包括一个整数n,(1 ≤ n ≤ 10^5)
 *
 * <p>输出描述: 输出一个整数,表示n的相反数
 *
 * <p>输入例子1: 1325
 *
 * <p>输出例子1: 6556
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-16
 * @version 1.0
 */
public class Main1 {

    /** @param args */
    public static void main(String[] args) {
        Main1 main = new Main1();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int s = scanner.nextInt();
        String string = String.valueOf(s);
        char[] ss = string.toCharArray();
        int result = main.reverseCharArray(ss, 0, ss.length - 1);
        System.out.println(result);
        scanner.close();
    }

    /**
     * @description 数组逆转
     * @param t
     * @param begin
     * @param end
     * @return
     */
    public int reverseCharArray(char[] t, int begin, int end) {
        char[] t1 = t.clone();
        while (end > begin) {
            char temp = t1[begin];
            t1[begin] = t1[end];
            t1[end] = temp;
            begin++;
            end--;
        }
        int res = Integer.parseInt(String.valueOf(t1)) + Integer.parseInt(String.valueOf(t));
        return res;
    }
}
