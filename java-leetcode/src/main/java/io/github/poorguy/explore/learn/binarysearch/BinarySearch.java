/* All Contributors (C) 2020 */
package io.github.poorguy.explore.learn.binarysearch;

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
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
