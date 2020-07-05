/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 数组中等差递增子区间的个数
 *
 * <p>413. Arithmetic Slices (Medium)
 *
 * <p>A = [1, 2, 3, 4] return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3,4] and [1, 2, 3, 4]
 * itself. dp[i] 表示以 A[i] 为结尾的等差递增子区间的个数。
 *
 * <p>如果 A[i] - A[i - 1] == A[i - 1] - A[i - 2]，表示 [A[i - 2], A[i - 1],A[i]]是一个等差递增子区间。 如果 [A[i -
 * 3], A[i - 2], A[i - 1]] 是一个等差递增子区间，那么 [A[i - 3], A[i -2], A[i - 1], A[i]] 也是。 因此在这个条件下，dp[i] =
 * dp[i-1] + 1。
 *
 * @author 梦境迷离.
 * @time 2018年6月18日
 * @version v1.0
 */
public class Leetcode_413_Interval {

    public int numberOfArithmeticSlices(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length;
        int[] dp = new int[n];
        for (int i = 2; i < n; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        int total = 0;
        for (int cnt : dp) {
            total += cnt;
        }
        return total;
    }
}
