/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.HashMap;
import java.util.Map;

class Pow {
    public static Map<Long, Double> cache = new HashMap<>();

    public double myPow(double x, int n) {
        cache.clear();
        if (n > 0) {
            return tailR(x, (long) n);
        } else if (n < 0) {
            return 1d / tailR(x, -(long) n);
        } else {
            return 1d;
        }
    }

    private double bruteForce(double x, long n) {
        double result = x;
        for (int i = 0; i < n - 1; i++) {
            result = result * x;
        }
        return result;
    }

    private double tailR(double x, long n) {
        if (n == 1) {
            return x;
        } else {
            double middleResult;
            if (cache.containsKey(n / 2)) {
                middleResult = cache.get(n / 2);
            } else {
                middleResult = tailR(x, n / 2);
                cache.put(n / 2, middleResult);
            }
            if (n % 2 == 0) {
                return middleResult * middleResult;
            } else {
                return middleResult * middleResult * x;
            }
        }
    }
}
