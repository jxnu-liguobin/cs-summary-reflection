/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

import java.util.HashMap;
import java.util.Map;

class ClimbingStairs {
    public static Map<Integer, Integer> cache = new HashMap<>();

    public int climbStairs(int n) {
        cache.put(0, 1);
        cache.put(1, 1);

        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        if (!cache.containsKey(n - 1)) {
            cache.put(n - 1, climbStairs(n - 1));
        }
        return cache.get(n - 1) + cache.get(n - 2);
    }
}
