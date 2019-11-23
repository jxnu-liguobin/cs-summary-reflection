package io.github.dreamylost.tooffer;

/**
 * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
 */
public class T12 {

    //偷懒
    public double Power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    public double Power1(double base, int exponent) {
        double temp = 1;
        for (int i = 1; i <= Math.abs(exponent); i++) {
            temp *= base;
        }
        return exponent < 0 ? (1 / temp) : temp;
    }

    /**
     * 1.全面考察指数的正负、底数是否为零等情况。
     * 2.写出指数的二进制表达，例如13表达为二进制1101。
     * 3.举例:10^1101 = 10^0001*10^0100*10^1000。
     * 4.通过&1和>>1来逐位读取1101，为1时将该位代表的乘数累乘到最终结果。
     */
    public double Power3(double base, int n) {
        double res = 1, curr = base;
        int exponent;
        if (n > 0) {
            exponent = n;
        } else if (n < 0) {
            if (base == 0)
                throw new RuntimeException("分母不能为0");
            exponent = -n;
        } else {// n==0
            return 1;// 0的0次方
        }
        while (exponent != 0) {
            if ((exponent & 1) == 1)
                res *= curr;
            curr *= curr;// 翻倍
            exponent >>= 1;// 右移一位
        }
        return n >= 0 ? res : (1 / res);
    }
}
