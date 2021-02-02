/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

/** The overall run time complexity should be O(log (m+n)). */
class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return nlognVersion(nums1, nums2);
    }

    // not the best solution
    private double nlognVersion(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length + nums2.length];
        int p1 = 0;
        int p2 = 0;
        int i = 0;
        while (p1 != nums1.length || p2 != nums2.length) {
            if (p1 == nums1.length) {
                nums[i] = nums2[p2];
                p2++;
            } else if (p2 == nums2.length) {
                nums[i] = nums1[p1];
                p1++;
            } else if (nums1[p1] < nums2[p2]) {
                nums[i] = nums1[p1];
                p1++;
            } else {
                nums[i] = nums2[p2];
                p2++;
            }
            i++;
        }
        if (nums.length % 2 == 0) {
            double val1 = nums[nums.length / 2 - 1];
            double val2 = nums[nums.length / 2];
            return (val1 + val2) / 2;
        } else {
            return nums[nums.length / 2];
        }
    }
}
