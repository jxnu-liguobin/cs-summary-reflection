/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// todo try to solve it by dynamic programming
class MinimumCostTreeFromLeafValues {
    // read the problem carefully!!!!
    public int mctFromLeafValues(int[] arr) {
        if (arr == null || arr.length < 2) {
            return -1;
        }

        int sum = 0;
        List<Integer> nums = Arrays.stream(arr).boxed().collect(Collectors.toList());
        while (nums.size() > 1) {
            int minIndex = min(nums);
            if (0 < minIndex && minIndex < nums.size() - 1) {
                int left = nums.get(minIndex - 1);
                int right = nums.get(minIndex + 1);
                sum += nums.get(minIndex) * Math.min(left, right);
            } else if (minIndex == 0) {
                sum += nums.get(minIndex) * nums.get(minIndex + 1);
            } else {
                sum += nums.get(minIndex) * nums.get(minIndex - 1);
            }
            nums.remove(minIndex);
        }
        return sum;
    }

    // nums!=null && nums.size>0
    private int min(List<Integer> nums) {
        int index = 0;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(index)) {
                index = i;
            }
        }
        return index;
    }
}
