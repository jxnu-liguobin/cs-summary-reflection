/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.*;

public class RandomizedSet {
    private List<Integer> list = null;
    private Map<Integer, Integer> map = null; // key: added value; value: index of list
    private Random rand = new Random();

    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified
     * element.
     */
    public boolean insert(int val) {
        if (this.map.containsKey(val)) {
            return false;
        } else {
            this.map.put(val, this.list.size());
            this.list.add(val);
            return true;
        }
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (this.map.containsKey(val)) {
            int originalIndex = this.map.get(val);
            Collections.swap(this.list, originalIndex, this.list.size() - 1);
            int lastVal = this.list.get(originalIndex);
            this.list.remove(this.list.size() - 1);
            this.map.put(lastVal, originalIndex);
            this.map.remove(val);
            return true;
        } else {
            return false;
        }
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rand.nextInt(map.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such: RandomizedSet obj = new
 * RandomizedSet(); boolean param_1 = obj.insert(val); boolean param_2 = obj.remove(val); int
 * param_3 = obj.getRandom();
 */
