/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 3的n次方
 *
 * @author 梦境迷离.
 * @time 2018年6月27日
 * @version v1.0
 */
public class Leetcode_326_Math {
    public static void main(String[] args) {
        int i = Leetcode_326_Math.getMaxPowerOfThree();
        System.out.println(i);
    }

    public boolean isPowerOfThree(int n) {
        // 1162261467 is 3^19, 3^20 is bigger than int
        return (n > 0 && 1162261467 % n == 0);
    }

    /**
     * 最大的3的幂次
     *
     * <p>这里求出1162261467
     */
    public static int getMaxPowerOfThree() {
        int max = 1;
        while (max * 3 > max) max *= 3;
        return max;
    }
}
