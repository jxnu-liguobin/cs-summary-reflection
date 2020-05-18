package io.github.dreamylost;

/**
 * 7 进制
 *
 * <p>504. Base 7 (Easy)
 *
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_504_Math {

    public static String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        boolean isNegative = num < 0;
        if (isNegative) {
            num = -num;
        }
        while (num > 0) {
            sb.append(num % 7);
            num /= 7;
        }
        String ret = sb.reverse().toString();
        return isNegative ? "-" + ret : ret;
    }

    // Java 中 static String toString(int num, int radix) 可以将一个整数转换为 redix 进制表示的字符串。
    public static String convertToBase_7(int num) {
        return Integer.toString(num, 7);
    }
}
