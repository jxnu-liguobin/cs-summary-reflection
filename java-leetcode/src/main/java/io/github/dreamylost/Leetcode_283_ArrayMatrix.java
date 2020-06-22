/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 把数组中的 0 移到末尾
 *
 * <p>283. Move Zeroes (Easy)
 *
 * <p>For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3,
 * 12, 0, 0].
 *
 * @author 梦境迷离.
 * @time 2018年7月11日
 * @version v1.0
 */
public class Leetcode_283_ArrayMatrix {

    public void moveZeroes(int[] nums) {
        int idx = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[idx++] = num;
            }
        }
        while (idx < nums.length) {
            nums[idx++] = 0;
        }
    }
}
