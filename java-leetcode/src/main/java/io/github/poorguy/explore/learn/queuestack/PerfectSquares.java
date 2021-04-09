/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.*;

public class PerfectSquares {
    public int numSquares(int n) {
        Set<Integer> squareSet = new HashSet<>();
        for (int i = 1; i * i <= n; i++) {
            squareSet.add(i * i);
        }
        if (squareSet.contains(n)) {
            return 1;
        }

        int count = 1;
        Deque<Integer> queue = new ArrayDeque<>();
        for (Integer num : squareSet) {
            queue.addLast(n - num);
        }
        while (!queue.isEmpty()) {
            count++;
            Deque<Integer> newQueue = new ArrayDeque<>();
            while (!queue.isEmpty()) {
                Integer rest = queue.pollFirst();
                if (squareSet.contains(rest)) {
                    return count;
                } else {
                    for (Integer num : squareSet) {
                        if (num < rest) {
                            newQueue.addLast(rest - num);
                        }
                    }
                }
            }
            queue = newQueue;
        }
        return count;
    }

    public static void main(String[] args) {
        PerfectSquares perfectSquares = new PerfectSquares();
        System.out.println(perfectSquares.numSquares(7));
    }
}
