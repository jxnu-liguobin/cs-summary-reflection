/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.ArrayDeque;

/** explain:https://www.algotree.org/algorithms/stack_based/largest_rectangle_in_histogram/ */
class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            int start = i;
            while (!stack.isEmpty() && heights[i] < stack.peek()[1]) {
                int[] index = stack.pop();
                max = Math.max(index[1] * (i - index[0]), max);
                start = index[0];
            }
            stack.push(new int[] {start, heights[i]});
        }
        while (!stack.isEmpty()) {
            int[] index = stack.pop();
            max = Math.max(index[1] * (heights.length - index[0]), max);
        }
        return max;
    }
}
