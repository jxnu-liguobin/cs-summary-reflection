/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class SearchForARange {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[] {-1, -1};
        }
        if (nums.length == 1) {
            if (nums[0] == target) {
                return new int[] {0, 0};
            }
        }
        int left = 0;
        int right = nums.length - 1;
        Integer firstTarget = null;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                firstTarget = mid;
                break;
            }
        }
        if (firstTarget == null) {
            return new int[] {-1, -1};
        }

        int[] result = new int[] {-1, -1};
        int leftLeft = 0;
        int leftRight = firstTarget;
        int rightLeft = firstTarget;
        int rightRight = nums.length - 1;
        while (leftLeft < leftRight - 1) {
            int mid = leftLeft + (leftRight - leftLeft) / 2;
            if (nums[mid] < target) {
                leftLeft = mid + 1;
            } else if (nums[mid] == target) {
                leftRight = mid;
            }
        }
        if (nums[leftLeft] == target) {
            result[0] = leftLeft;
        } else {
            result[0] = leftRight;
        }
        while (rightLeft + 1 < rightRight) {
            int mid = rightLeft + (rightRight - rightLeft) / 2;
            if (nums[mid] > target) {
                rightRight = mid - 1;
            } else if (nums[mid] == target) {
                rightLeft = mid;
            }
        }
        if (nums[rightRight] == target) {
            result[1] = rightRight;
        } else {
            result[1] = rightLeft;
        }
        return result;
    }
}
