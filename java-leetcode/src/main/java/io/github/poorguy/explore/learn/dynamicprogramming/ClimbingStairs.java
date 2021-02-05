/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

class ClimbingStairs {
    public static Map<Integer, Integer> cache = new HashMap<>();

    public int climbStairs(int n) {
        return dynamicProgramming(n);
    }

    private int cachedRecursion(int n) {
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

    private int dynamicProgramming(int n) {
        int[] dp = new int[n + 2];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[dp.length - 1];
    }
}
