/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class MoveZeroes {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                j++;
            }
        }
        for (int i = 0; i < nums.length - j; i++) {
            nums[nums.length - 1 - i] = 0;
        }
    }
}
