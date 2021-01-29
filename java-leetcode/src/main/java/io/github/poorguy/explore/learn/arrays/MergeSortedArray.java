/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int leftPointer = m - 1;
        int rightPointer = n - 1;
        int rightEdge = m + n - 1;
        for (int i = 0; i < m + n; i++) {
            if (leftPointer == -1) {
                nums1[rightEdge - i] = nums2[rightPointer];
                rightPointer--;
                continue;
            } else if (rightPointer == -1) {
                nums1[rightEdge - i] = nums1[leftPointer];
                leftPointer--;
                continue;
            }
            if (nums1[leftPointer] > nums2[rightPointer]) {
                nums1[rightEdge - i] = nums1[leftPointer];
                leftPointer--;
            } else {
                nums1[rightEdge - i] = nums2[rightPointer];
                rightPointer--;
            }
        }
    }
}
