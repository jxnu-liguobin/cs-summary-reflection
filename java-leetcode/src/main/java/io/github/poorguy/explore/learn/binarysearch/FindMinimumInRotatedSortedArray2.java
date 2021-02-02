/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class FindMinimumInRotatedSortedArray2 {
    /** bad solution. should use binary search */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }
        int min = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        return min;
    }
}
