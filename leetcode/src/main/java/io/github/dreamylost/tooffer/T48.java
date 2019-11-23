package io.github.dreamylost.tooffer;

import java.math.BigInteger;

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
 */
public class T48 {

    public int Add(int num1, int num2) {
        BigInteger integer1 = new BigInteger(String.valueOf(num1));
        BigInteger integer2 = new BigInteger(String.valueOf(num2));
        return integer1.add(integer2).intValue();
    }

    public int Add1(int num1, int num2) {
        while (num2 != 0) {
            int temp = num1 ^ num2;
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }
}
