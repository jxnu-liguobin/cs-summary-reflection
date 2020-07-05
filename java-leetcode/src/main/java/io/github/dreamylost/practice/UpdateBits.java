/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * 给出两个32位的整数N和M，以及两个二进制位的位置i和j。写一个方法来使得N中的第i到j位等于M（M会是N中从第i为开始到第j位的子串） ？？
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
public class UpdateBits {

    public int updateBits(int n, int m, int i, int j) {
        int max = ~0; /* All 1’s */
        // 1’s through position j, then 0’s
        if (j == 31) j = max;
        else j = (1 << (j + 1)) - 1;
        int left = max - j;
        // 1’s after position i
        int right = ((1 << i) - 1);
        // 1’s, with 0s between i and j
        int mask = left | right;
        // Clear i through j, then put m in there
        return ((n & mask) | (m << i));
    }
}
