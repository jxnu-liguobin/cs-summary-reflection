/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 数组区间和
 *
 * <p>303. Range Sum Query - Immutable (Easy)
 *
 * <p>Given nums = [-2, 0, 3, -5, 2, -1]
 *
 * <p>sumRange(0, 2) -> 1 sumRange(2, 5) -> -1 sumRange(0, 5) -> -3
 *
 * @author 梦境迷离.
 * @time 2018年6月18日
 * @version v1.0
 */
public class Leetcode_303_Interval {

    private int[] sums;

    public void NumArray(int[] nums) {
        sums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sums[i] = i == 0 ? nums[0] : sums[i - 1] + nums[i];
        }
    }

    /** 求区间 i ~ j 的和，可以转换为 sum[j] - sum[i-1]，其中 sum[i] 为 0 ~ i 的和。 */
    public int sumRange(int i, int j) {
        return i == 0 ? sums[j] : sums[j] - sums[i - 1];
    }
}
