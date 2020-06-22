/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 面试题15. 二进制中1的个数
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2020-04-04
 */
public class Leetcode_Interview_15 {

    public static void main(String[] args) {
        int ret = new Leetcode_Interview_15().hammingWeight(9);
        System.out.println(ret);
    }

    public int hammingWeight(int n) {
        int ret = 0;
        while (n != 0) {
            ret += n & 1;
            n >>>= 1; // >>=会超时
        }
        return ret;
    }
}
