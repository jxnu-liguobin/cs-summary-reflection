/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class FindMinimumInRotatedSortedArray {
    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        int last = nums[r];
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > last) {
                l = mid + 1;
            } else if (nums[mid] < last) {
                r = mid;
            }
        }
        return nums[r];
    }
}
