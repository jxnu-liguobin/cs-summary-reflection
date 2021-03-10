/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

import java.util.HashMap;
import java.util.Map;

class MapSumPairs {

    private Character ch;
    private Map<Character, MapSumPairs> children;
    private int val;

    /** Initialize your data structure here. */
    public MapSumPairs() {
        this.ch = null;
        this.children = null;
        this.val = 0;
    }

    public void insert(String key, int val) {
        char[] chars = key.toCharArray();
        MapSumPairs pointer = this;
        for (char c : chars) {
            if (pointer.children == null) {
                pointer.children = new HashMap<>();
            }
            if (pointer.children.containsKey(c)) {
                pointer = pointer.children.get(c);
            } else {
                MapSumPairs mapSum = new MapSumPairs();
                mapSum.ch = c;
                pointer.children.put(c, mapSum);
                pointer = mapSum;
            }
        }
        pointer.val = val;
    }

    public int sum(String prefix) {
        MapSumPairs pointer = this;
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            if (pointer.children == null) {
                return 0;
            }
            MapSumPairs child = pointer.children.get(c);
            if (child == null) {
                return 0;
            }
            pointer = child;
        }
        return sum(pointer);
    }

    private int sum(MapSumPairs mapSum) {
        if (mapSum == null) {
            return 0;
        }
        if (mapSum.children == null || mapSum.children.isEmpty()) {
            return mapSum.val;
        }
        int sum = 0;
        for (MapSumPairs child : mapSum.children.values()) {
            sum += sum(child);
        }
        return mapSum.val + sum;
    }
}

/**
 * Your MapSum object will be instantiated and called as such: MapSum obj = new MapSum();
 * obj.insert(key,val); int param_2 = obj.sum(prefix);
 */
