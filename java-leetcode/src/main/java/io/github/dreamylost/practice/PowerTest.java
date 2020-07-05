/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
 * @author Mr.Li
 */
public class PowerTest {

    public static void main(String[] args) {
        System.out.println(new PowerTest().Power(1.2, 2));
        System.out.println(new PowerTest().Power2(1.2, 2));
    }

    /**
     * @description API
     * @param base
     * @param exponent
     * @return
     */
    public double Power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * @description 移位
     * @param base
     * @param n
     * @return
     */
    public double Power2(double base, int n) {
        double res = 1, curr = base;
        int exponent;
        if (n > 0) {
            exponent = n;
        } else if (n < 0) {
            if (base == 0) throw new RuntimeException("分母不能为0");
            exponent = -n;
        } else {
            // n==0
            return 1; // 0的0次方
        }
        while (exponent != 0) {
            if ((exponent & 1) == 1) res *= curr;
            curr *= curr; // 翻倍
            exponent >>= 1; // 右移一位
        }
        return n >= 0 ? res : (1 / res);
    }
}
