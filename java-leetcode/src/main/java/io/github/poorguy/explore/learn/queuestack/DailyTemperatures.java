/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayDeque;
import java.util.Deque;

class DailyTemperatures {
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = T.length - 1; i >= 0; i--) {
            int today = T[i];
            while (!stack.isEmpty()) {
                if (T[stack.peek()] <= today) {
                    stack.pop();
                } else {
                    result[i] = stack.peek() - i;
                    break;
                }
            }
            stack.push(i);
            if (stack.isEmpty()) {
                result[i] = 0;
            }
        }
        return result;
    }
}
