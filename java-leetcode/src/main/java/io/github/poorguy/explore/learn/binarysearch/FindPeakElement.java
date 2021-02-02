/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class FindPeakElement {
    public int findPeakElement(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            if (start == end) {
                return start;
            }
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[mid + 1]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return end;
    }
}
