/* All Contributors (C) 2020 */
package io.github.poorguy;

/**
 * Given a sorted (in ascending order) integer array nums of n elements and a target value, write a
 * function to search target in nums. If target exists, then return its index, otherwise return -1.
 *
 * <p>Writing a bug-free binary search is not easy.
 *
 * @author poorguy
 * @date 2020年7月22日
 */
public class BinarySearch {
    public static int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length;
        int middle = (left + right) / 2;
        while (left < right) {
            if (target < nums[middle]) {
                right = middle - 1;
                middle = (left + right) / 2;
            } else if (target > nums[middle]) {
                // 这种写法像是补丁。是根据测试用例凑出来的，非常不好
                if (middle + 1 >= nums.length) {
                    return -1;
                }
                left = middle + 1;
                middle = (left + right) / 2;
            } else {
                return middle;
            }
        }
        if (nums[left] == target) {
            return left;
        } else {
            return -1;
        }
    }
}
