/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.HashMap;
import java.util.Map;

// only need the first two, no need to save last two
class FourSum2 {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> firstTwo =
                new HashMap<>(); // key: sum of element from first two array; value: count
        Map<Integer, Integer> lastTwo = new HashMap<>();
        for (int i : A) {
            for (int j : B) {
                if (firstTwo.containsKey(i + j)) {
                    firstTwo.put(i + j, firstTwo.get(i + j) + 1);
                } else {
                    firstTwo.put(i + j, 1);
                }
            }
        }
        for (int i : C) {
            for (int j : D) {
                if (lastTwo.containsKey(i + j)) {
                    lastTwo.put(i + j, lastTwo.get(i + j) + 1);
                } else {
                    lastTwo.put(i + j, 1);
                }
            }
        }

        int count = 0;
        for (Integer key : firstTwo.keySet()) {
            Integer needFind = -key;
            if (lastTwo.containsKey(needFind)) {
                count += lastTwo.get(needFind) * firstTwo.get(key);
            }
        }
        return count;
    }
}
