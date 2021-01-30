/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

import java.util.HashMap;
import java.util.Map;

class FibonacciNumber {
    public static Map<Integer, Integer> cache = new HashMap<>();

    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int fib1, fib2;
        if (!cache.containsKey(n - 1)) {
            cache.put(n - 1, fib(n - 1));
        }
        fib1 = cache.get(n - 1);
        if (!cache.containsKey(n - 2)) {
            cache.put(n - 2, fib(n - 2));
        }
        fib2 = cache.get(n - 2);
        return fib1 + fib2;
    }
}
