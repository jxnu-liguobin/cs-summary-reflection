/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 713. 乘积小于K的子数组
 *
 * <p>给定一个正整数数组 nums。 找出该数组内乘积小于 k 的连续的子数组的个数。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_713_Double_Pointer {

    /**
     * 双指针
     *
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int len = nums.length;
        long p = 1l;
        int i = 0;
        int j = 0;
        int total = 0;
        while (j < len) {
            p *= nums[j];
            // 如果乘积大于k，那么试着减少左边的
            while (i <= j && p >= k) {
                p /= nums[i];
                i++;
            }
            // 每个步骤引入x个新子数组，其中x是当前窗口的大小(j - i + 1);
            total += (j - i + 1);
            j++;
        }
        return total;
    }
}
