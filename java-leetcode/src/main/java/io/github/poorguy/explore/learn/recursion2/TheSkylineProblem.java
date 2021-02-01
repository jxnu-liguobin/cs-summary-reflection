/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.*;

/** It can be solved by divide and conquer or inverted index as well */
class TheSkylineProblem {
    private static class Edge implements Comparable<Edge> {
        int x;
        int y;

        public Edge(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Edge e) {
            if (this.x != e.x) {
                return this.x - e.x;
            } else {
                return e.y - y;
            }
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<Edge> edgeList = new ArrayList<>();
        for (int[] edge : buildings) {
            edgeList.add(new Edge(edge[0], edge[2]));
            edgeList.add(new Edge(edge[1], -edge[2]));
        }
        Collections.sort(edgeList);

        List<List<Integer>> result = new ArrayList<>();
        PriorityQueue<Integer> bigTopHeap = new PriorityQueue<>(Collections.reverseOrder());
        // map to count same height edge
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Edge edge : edgeList) {
            if (edge.y > 0) {
                if (bigTopHeap.isEmpty() || edge.y > bigTopHeap.peek()) {
                    List<Integer> point = new ArrayList<>();
                    point.add(edge.x);
                    point.add(edge.y);
                    result.add(point);
                }
                if (!countMap.containsKey(edge.y) || countMap.get(edge.y) == 0) {
                    bigTopHeap.add(edge.y);
                }
                countMap.put(edge.y, countMap.containsKey(edge.y) ? countMap.get(edge.y) + 1 : 1);
            } else {
                Integer count = countMap.get(-edge.y);
                // Only if there is no more edge with the same height, the height will be changed.
                if (bigTopHeap.peek() == -edge.y && count == 1) {
                    bigTopHeap.poll();
                    while (!bigTopHeap.isEmpty() && countMap.get(bigTopHeap.peek()) == 0) {
                        bigTopHeap.poll();
                    }
                    List<Integer> point = new ArrayList<>();
                    point.add(edge.x);
                    point.add(bigTopHeap.isEmpty() ? 0 : bigTopHeap.peek());
                    result.add(point);
                }
                countMap.put(-edge.y, count - 1);
            }
        }

        return result;
    }
}
