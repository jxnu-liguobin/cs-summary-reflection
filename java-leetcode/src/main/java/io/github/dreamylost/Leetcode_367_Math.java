package io.github.dreamylost;

/**
 * 平方数
 *
 * <p>367. Valid Perfect Square (Easy)
 *
 * <p>Input: 16 Returns: True 平方序列：1,4,9,16,..
 *
 * <p>间隔：3,5,7,...
 *
 * <p>间隔为等差数列，使用这个特性可以得到从 1 开始的平方序列。
 *
 * @author 梦境迷离.
 * @time 2018年6月26日
 * @version v1.0
 */
public class Leetcode_367_Math {
    public static void main(String[] args) {
        boolean t = Leetcode_367_Math.isPerfectSquare(17);
        System.out.println(t);
    }

    /** 存在num=1-3-9-(2n-1) 即可 */
    public static boolean isPerfectSquare(int num) {
        int subNum = 1;
        while (num > 0) {
            num -= subNum;
            subNum += 2;
        }
        return num == 0;
    }
}
