/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

/**
 * bad solution. Can be searched for outer sides, but I need a prove If the numbers can't repeat, we
 * can use hashMap (to optimize it, we can still find target index every time we add a new element).
 *
 * <p>prove: If you have finished leetcode problem "240. Search a 2D Matrix II", you will find that
 * this is exactly the same problem,
 */
class TwoSum2 {
    // number can repeat
    // time complexity: O(nlogn)
    // there must be one and only one solution
    public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        int top = numbers.length - 1;
        for (int i = 0; i < numbers.length - 1; i++) {
            int needFind = target - numbers[i];
            int l = i + 1;
            int r = top;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (numbers[mid] == needFind) {
                    result[0] = i + 1;
                    result[1] = mid + 1;
                    return result;
                } else if (numbers[mid] < needFind) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return null;
    }
}
