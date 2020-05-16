package io.github.dreamylost.practice;

public class IsThePowerOf2 {

    /**
     * @description 给定一个整数，判断它是否是2的方幂
     * @param args
     */
    public static void main(String[] args) {
        long n = 2;
        int c = powerOf2(n);
        if (c == -1) {
            System.out.println(n + "不是2的方幂");
        } else {
            System.out.println("n=" + n + "::2^" + c);
        }
    }

    private static boolean isPowerOf2(long n) {
        boolean flag = false;
        if (n > 0 && (n & (n - 1)) == 0) {
            flag = true;
            return flag;
        }
        return flag;
    }

    public static int powerOf2(long n) {
        int count = 0;
        if (isPowerOf2(n)) {
            while (n != 1) {
                n >>= 1;
                count++;
            }
            return count;
        } else {
            return -1;
        }
    }
}
