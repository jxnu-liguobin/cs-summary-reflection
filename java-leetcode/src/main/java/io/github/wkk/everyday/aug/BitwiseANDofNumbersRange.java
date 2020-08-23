/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * 题目: 数字范围按位与
 *
 * <p>例子: 输入: [5, 7], 输出 4
 *
 * <p>思路: 位运算
 *
 * <p>&(与): 1. 任何数和1&均为其本身 2. 任何数和0&均为0
 *
 * @author kongwiki@163.com
 * @since 2020/8/23下午2:51
 */
public class BitwiseANDofNumbersRange {
    /** 方法一: 找出公共前缀 */
    public int rangeBitwiseAnd(int m, int n) {
        int shift = 0;
        // 找到公共前缀
        while (m < n) {
            m >>= 1;
            n >>= 1;
            ++shift;
        }
        return m << shift;
    }

    /** 方法二: 类似于offer第15题 */
    public static int rangeBitwiseAndII(int m, int n) {
        while (m < n) {
            n = n & (n - 1);
        }
        return n;
    }

    public static void main(String[] args) {
        int m = 5;
        int n = 7;
        rangeBitwiseAndII(m, n);
    }
}
