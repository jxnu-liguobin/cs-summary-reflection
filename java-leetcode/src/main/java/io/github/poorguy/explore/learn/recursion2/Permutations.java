/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.ArrayList;
import java.util.List;

class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        recursive(nums, null, result);
        return result;
    }

    private void recursive(int[] nums, List<Integer> midList, List<List<Integer>> result) {
        if (nums.length == 0) {
            result.add(midList);
            return;
        }

        for (int num : nums) {
            if (midList.contains(num)) {
                continue;
            }
            midList.add(num);
            recursive(nums, midList, result);
            midList.remove(midList.size() - 1);
        }
    }
}
