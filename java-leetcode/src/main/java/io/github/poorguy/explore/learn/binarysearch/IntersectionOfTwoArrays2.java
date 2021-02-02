/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.*;

class IntersectionOfTwoArrays2 {
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> countMap = new HashMap<>();
        boolean isFirstShorter = nums1.length < nums2.length;
        if (isFirstShorter) {
            for (int num : nums2) {
                if (countMap.containsKey(num)) {
                    countMap.put(num, countMap.get(num) + 1);
                } else {
                    countMap.put(num, 1);
                }
            }
        } else {
            for (int num : nums1) {
                if (countMap.containsKey(num)) {
                    countMap.put(num, countMap.get(num) + 1);
                } else {
                    countMap.put(num, 1);
                }
            }
        }
        List<Integer> resultList = new ArrayList<>();
        if (isFirstShorter) {
            for (int num : nums1) {
                if (countMap.containsKey(num)) {
                    resultList.add(num);
                    int count = countMap.get(num);
                    if (count == 1) {
                        countMap.remove(num);
                    } else {
                        countMap.put(num, count - 1);
                    }
                }
            }
        } else {
            for (int num : nums2) {
                if (countMap.containsKey(num)) {
                    resultList.add(num);
                    int count = countMap.get(num);
                    if (count == 1) {
                        countMap.remove(num);
                    } else {
                        countMap.put(num, count - 1);
                    }
                }
            }
        }
        return resultList.stream().mapToInt(x -> x).toArray();
    }
}
