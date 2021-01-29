/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

/** find the closest to 0 and start for there. or use a linked list with a few pointer */
class SquaresOfASortedArray {
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        if (nums.length == 1) {
            result = new int[] {nums[0] * nums[0]};
        }
        int minIndex = 0;
        int min = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] * nums[i] < min * min) {
                minIndex = i;
                min = nums[i];
            }
        }
        int mid = minIndex;
        int left, right;
        if (mid - 1 >= 0) {
            left = mid - 1;
            right = mid;
        } else {
            left = mid;
            right = mid + 1;
        }
        for (int i = 0; i < result.length; i++) {
            if (right >= nums.length) {
                result[i] = nums[left] * nums[left];
                left--;
                continue;
            }
            if (left < 0) {
                result[i] = nums[right] * nums[right];
                right++;
                continue;
            }
            if (nums[left] * nums[left] > nums[right] * nums[right]) {
                result[i] = nums[right] * nums[right];
                right++;
            } else {
                result[i] = nums[left] * nums[left];
                left--;
            }
        }
        return result;
    }
}
