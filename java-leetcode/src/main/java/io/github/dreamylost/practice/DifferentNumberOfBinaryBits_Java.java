/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * 两个整数的二进制有多少不同位
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
public class DifferentNumberOfBinaryBits_Java {

    public static int bitSwapRequired(int a, int b) {
        int count = 0;
        for (int c = a ^ b; c != 0; c = c >>> 1) {
            count += c & 1;
        }
        return count;
    }
}
