/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

import java.util.Arrays;

/** 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。 */
public class T32 {

    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return "";
        }
        int len = numbers.length;
        String[] str = new String[len];
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(
                str,
                (o1, o2) -> {
                    String c1 = o1 + o2;
                    String c2 = o2 + o1;
                    return c1.compareTo(c2);
                });
        for (int i = 0; i < len; i++) {
            stringBuffer.append(str[i]);
        }
        return stringBuffer.toString();
    }
}
