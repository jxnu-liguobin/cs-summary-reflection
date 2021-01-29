/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

import java.util.HashMap;
import java.util.Map;

class CheckIfNAndItsDoubleExist {
    public boolean checkIfExist(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i] * 2) && map.get(arr[i] * 2) != i) {
                return true;
            }
        }
        return false;
    }
}
