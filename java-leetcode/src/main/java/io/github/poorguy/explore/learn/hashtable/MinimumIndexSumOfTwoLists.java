/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MinimumIndexSumOfTwoLists {
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int minDistance = list1.length + list2.length - 2;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list2.length; i++) {
            Integer index = map.get(list2[i]);
            if (index != null) {
                if (minDistance > i + index) {
                    minDistance = i + index;
                    result = new ArrayList<>();
                    result.add(list2[i]);
                } else if (i + index == minDistance) {
                    result.add(list2[i]);
                }
            }
        }
        return result.toArray(new String[0]);
    }
}
