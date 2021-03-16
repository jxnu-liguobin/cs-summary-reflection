/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.*;

class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        PriorityQueue<Integer> bigTopHeap = new PriorityQueue<>(Collections.reverseOrder());
        bigTopHeap.addAll(map.values());
        int topK = 0;
        for (int i = 0; i < k; i++) {
            topK = bigTopHeap.poll();
        }
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= topK) {
                result.add(entry.getKey());
            }
        }

        int[] finalResult = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            finalResult[i] = result.get(i);
        }
        return finalResult;
    }
}
