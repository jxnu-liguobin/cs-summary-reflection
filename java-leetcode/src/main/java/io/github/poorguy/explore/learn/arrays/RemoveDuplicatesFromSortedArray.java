/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int removedCount = 0;
        int lastNum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i + removedCount == nums.length) {
                break;
            }

            nums[i] = nums[i + removedCount];

            if (nums[i] == lastNum) {
                removedCount++;
                i--;
            } else {
                lastNum = nums[i];
            }
        }
        return nums.length - removedCount;
    }
}
