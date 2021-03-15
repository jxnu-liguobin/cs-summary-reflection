/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.HashSet;
import java.util.Set;

class HappyNumber {
    public boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }

        Set<Integer> set = new HashSet<>();
        set.add(n);
        while (true) {
            int val = calculate(n);
            if (set.contains(val)) {
                return false;
            }
            if (val == 1) {
                return true;
            } else {
                set.add(val);
            }
            n = val;
        }
    }

    private int calculate(int n) {
        int val = n;
        int result = 0;
        while (val > 0) {
            int num = val % 10;
            result += num * num;
            val = val / 10;
        }
        return result;
    }
}
