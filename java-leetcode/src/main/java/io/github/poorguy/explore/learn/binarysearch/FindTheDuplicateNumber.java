/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.HashSet;
import java.util.Set;

/** Only one repeat, but can repeat many times */
class FindTheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        return lowTimeComplexity(nums);
    }

    // 1. fast slow pointers
    private int lowSpaceComplexity(int[] nums) {

        // assert false
        return Integer.MIN_VALUE;
    }

    // bad solution. There is a better solution
    private int lowTimeComplexity(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            if (hashSet.contains(num)) {
                return num;
            } else {
                hashSet.add(num);
            }
        }
        // assert false
        return Integer.MIN_VALUE;
    }
}
