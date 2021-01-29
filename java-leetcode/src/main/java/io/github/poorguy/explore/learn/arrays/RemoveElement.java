/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class RemoveElement {
    public int removeElement(int[] nums, int val) {
        int moveCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (moveCount + i == nums.length) {
                break;
            }

            nums[i] = nums[i + moveCount];

            if (nums[i] == val) {
                moveCount++;
                i--;
            }
        }
        return nums.length - moveCount;
    }
}
