/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.PriorityQueue;

class FindKthSmallestPairDistance {
    private static class Pair implements Comparable<Pair> {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair pair) {
            return Math.abs(this.x - this.y) - Math.abs(pair.x - pair.y);
        }
    }

    public int smallestDistancePair(int[] nums, int k) {
        return solution(nums, k);
    }

    private int solution(int[] nums, int k) {
        return 0;
    }

    /** memory limit exceeded */
    private int minTopHeapSolution(int[] nums, int k) {
        PriorityQueue<Pair> minTopHeap = new PriorityQueue<>();
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                minTopHeap.add(new Pair(nums[i], nums[j]));
            }
        }
        Pair result = null;
        for (int i = 0; i < k; i++) {
            result = minTopHeap.poll();
        }
        return Math.abs(result.x - result.y);
    }
}
