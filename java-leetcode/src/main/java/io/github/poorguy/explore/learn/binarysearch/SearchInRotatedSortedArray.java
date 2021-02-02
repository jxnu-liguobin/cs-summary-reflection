/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

/** bad solution, no need to rotate to origin order. */
class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int length = nums.length;
        if (length == 1) {
            if (nums[0] == target) {
                return 0;
            } else {
                return -1;
            }
        }

        int index = 0;
        int moveLength;
        boolean isLastNormalDirection = true;
        moveLength = getMoveLength(length);
        while (moveLength != 0) {
            int oldIndex = index;
            if (nums[index] == target) {
                return index;
            } else if (nums[index] > target) {
                if (isLastNormalDirection) {
                    index = moveIndexToLeft(index, moveLength, length);
                } else {
                    index = moveIndexToRight(index, moveLength, length);
                }
                if (nums[oldIndex] < nums[index]) {
                    isLastNormalDirection = false;
                } else {
                    isLastNormalDirection = true;
                }
            } else {
                if (isLastNormalDirection) {
                    index = moveIndexToRight(index, moveLength, length);
                } else {
                    index = moveIndexToLeft(index, moveLength, length);
                }
                if (nums[oldIndex] > nums[index]) {
                    isLastNormalDirection = false;
                } else {
                    isLastNormalDirection = true;
                }
            }
            moveLength = getMoveLength(moveLength);
        }
        if (nums[index] == target) {
            return index;
        } else {
            return -1;
        }
    }

    private int moveIndexToLeft(int startIndex, int moveLength, int arrayLength) {
        startIndex = startIndex - moveLength;
        if (startIndex < 0) {
            startIndex = arrayLength + startIndex;
        }
        return startIndex;
    }

    private int moveIndexToRight(int startIndex, int moveLength, int arrayLength) {
        startIndex = startIndex + moveLength;
        if (startIndex > arrayLength - 1) {
            startIndex = startIndex - moveLength;
        }
        return startIndex;
    }

    /** calculate index move distance */
    private int getMoveLength(int length) {
        if (length == 1) {
            return 0;
        }
        int moveLength;
        if (length % 2 == 0) {
            moveLength = length / 2;
        } else {
            moveLength = length / 2 + 1;
        }
        return moveLength;
    }
}
