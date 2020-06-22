/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.ArrayList;

/**
 * @description 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 *     例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 * @author Mr.Li
 */
public class PrintMinNumber {
    public static void main(String[] args) {
        int[] s = {3, 32, 321};
        String string = new PrintMinNumber().printMinNumber(s);
        System.out.println(string);
    }

    public String printMinNumber(int[] numbers) {

        int n = numbers.length;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            list.add(numbers[i]);
        }
        java.util.Collections.sort(
                list,
                new java.util.Comparator<Integer>() {

                    @Override
                    public int compare(Integer str1, Integer str2) {
                        String s1 = str1 + "" + str2;
                        String s2 = str2 + "" + str1;

                        return s1.compareTo(s2);
                    }
                });
        StringBuilder s = new StringBuilder();
        for (int j : list) {
            s.append(String.valueOf(j));
        }
        return s.toString();
    }
}
