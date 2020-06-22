/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 题目描述：一个有序数组只有一个数不出现两次，找出这个数。
 * @time 2018年3月30日
 */
public class Leetcode_540 {

    public int singleNonDuplicate(int[] nums) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int m = l + (h - l) / 2;
            // 这个数必须在偶数上 如: 11 22 33 x 44 55
            if (m % 2 == 1) m--; // 保证 l/h/m 都在偶数位，使得查找区间大小一直都是 奇数
            if (nums[m] == nums[m + 1]) l = m + 2;
            else h = m;
        }
        return nums[l];
    }
}
