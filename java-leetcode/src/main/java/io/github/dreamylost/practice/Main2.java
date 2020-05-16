package io.github.dreamylost.practice;

/**
 * Title: Mian2.java
 *
 * <p>Description: 一个由小写字母组成的字符串可以看成一些同一字母的最大碎片组成的。例如,"aaabbaaac"是由下面碎片组成的:
 * 'aaa','bb','c'。牛牛现在给定一个字符串,请你帮助计算这个字符串的所有碎片的平均长度是多少。 输入描述: 输入包括一个字符串s,字符串s的长度length(1 ≤ length ≤
 * 50),s只含小写字母('a'-'z') 输出描述: 输出一个整数,表示所有碎片的平均长度,四舍五入保留两位小数。 如样例所示: s = "aaabbaaac" 所有碎片的平均长度 = (3 +
 * 2 + 3 + 1) / 4 = 2.25
 *
 * <p>输入例子1: aaabbaaac 输出例子1: 2.25
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-16
 * @version 1.0
 */
public class Main2 {

    /** @param args */
    public static void main(String[] args) {
        Main2 main = new Main2();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String s = scanner.next();
        double re = main.getLength(s);
        System.out.println(String.format("%.2f", re));
        scanner.close();
    }

    public double getLength(String s) {
        // 获取第一个字符
        char ss = s.charAt(0);
        double count = 1;
        for (int i = 0; i < s.length(); i++) {
            // 不相等就加1
            if (ss != s.charAt(i)) {
                ss = s.charAt(i);
                count++;
            }
        }
        return (double) s.length() / count;
    }
}
