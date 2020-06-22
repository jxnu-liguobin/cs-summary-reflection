/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.dp;

/**
 * 845. 数组中的最长山脉 我们把数组 A 中符合下列属性的任意连续子数组 B 称为 “山脉”： B.length >= 3 存在 0 < i < B.length - 1 使得 B[0] <
 * B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1] （注意：B 可以是 A 的任意子数组，包括整个数组 A。） 给出一个整数数组
 * A，返回最长 “山脉” 的长度。 如果不含有 “山脉” 则返回 0。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_845_Dp {

    // DP
    public int longestMountain(int[] A) {
        int N = A.length, res = 0;
        int[] up = new int[N], down = new int[N];
        // 向左
        for (int i = N - 2; i >= 0; --i) {
            if (A[i] > A[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        // 向右
        for (int i = 0; i < N; ++i) {
            if (i > 0 && A[i] > A[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
            if (up[i] > 0 && down[i] > 0) {
                res = Math.max(res, up[i] + down[i] + 1);
            }
        }
        return res;
    }

    // DP优化后
    public int longestMountain2(int[] A) {
        int res = 0, up = 0, down = 0;
        for (int i = 1; i < A.length; ++i) {
            if (down > 0 && A[i - 1] < A[i] || A[i - 1] == A[i]) {
                up = down = 0;
            }
            if (A[i - 1] < A[i]) {
                up++;
            }
            if (A[i - 1] > A[i]) {
                down++;
            }
            if (up > 0 && down > 0 && up + down + 1 > res) {
                res = up + down + 1;
            }
        }
        return res;
    }

    public int longestMountain3(int[] A) {
        int n = A.length;
        if (n < 3) return 0;
        int left = 0;
        int right;
        int max = 0;
        while (left < n - 2) {
            while (left < n - 1 && A[left] >= A[left + 1]) {
                left++;
            }
            // 左边的山上顶点就是右边下山的起点
            right = left + 1;
            while (right < n - 1 && A[right] < A[right + 1]) {
                right++;
            }
            while (right < n - 1 && A[right] > A[right + 1]) {
                right++;
                max = Math.max(max, right - left + 1);
            }
            // 右边的山下就是左边上山的起点
            left = right;
        }
        return max;
    }
}
