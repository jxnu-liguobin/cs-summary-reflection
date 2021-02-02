/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.HashSet;
import java.util.Set;

class IntersectionOfTwoArrays {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        boolean isFirstShorter = nums1.length < nums2.length;
        if (isFirstShorter) {
            for (int num : nums2) {
                set.add(num);
            }
        } else {
            for (int num : nums1) {
                set.add(num);
            }
        }
        Set<Integer> resultSet = new HashSet<>();
        if (isFirstShorter) {
            for (int num : nums1) {
                if (set.contains(num)) {
                    resultSet.add(num);
                }
            }
        } else {
            for (int num : nums2) {
                if (set.contains(num)) {
                    resultSet.add(num);
                }
            }
        }
        return resultSet.stream().mapToInt(x -> x).toArray();
    }
}
