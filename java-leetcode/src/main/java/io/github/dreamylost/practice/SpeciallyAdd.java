/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.math.BigInteger;

/**
 * @description 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
 * @author Mr.Li
 */
public class SpeciallyAdd {
    public int Add2(int num1, int num2) {
        BigInteger bi1 = new BigInteger(String.valueOf(num1));
        BigInteger bi2 = new BigInteger(String.valueOf(num2));
        return bi1.add(bi2).intValue();
    }

    public int add(int num1, int num2) {
        while (num2 != 0) {
            // 计算未进位的值
            int temp = num1 ^ num2;
            // 计算进位的值，当进位的值==0的时候，num1是答案
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }
}
