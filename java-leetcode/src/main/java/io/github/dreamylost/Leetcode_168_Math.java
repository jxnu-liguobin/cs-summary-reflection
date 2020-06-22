/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 26 进制
 *
 * <p>168. Excel Sheet Column Title (Easy) 1 -> A 2 -> B 3 -> C ... 26 -> Z 27 ->AA 28 -> AB 因为是从 1
 * 开始计算的，而不是从 0 开始，因此需要对 n 执行 -1 操作。
 *
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_168_Math {

    public static void main(String[] args) {
        String ret = Leetcode_168_Math.convertToTitle(28);
        System.out.println(ret);
    }

    public static String convertToTitle(int n) {
        if (n == 0) {
            return "";
        }
        n--;
        return convertToTitle(n / 26) + (char) (n % 26 + 'A');
    }
}
