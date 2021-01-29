/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

import java.util.Arrays;

class HeightChecker {
    public int heightChecker(int[] heights) {
        int[] sorted = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            sorted[i] = heights[i];
        }
        Arrays.sort(sorted);
        int counter = 0;
        for (int i = 0; i < heights.length; i++) {
            if (sorted[i] != heights[i]) {
                counter++;
            }
        }
        return counter;
    }
}
