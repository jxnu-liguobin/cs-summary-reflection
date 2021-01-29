/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int counter = 0;
        int max = 0;
        for (int i : nums) {
            if (i == 1) {
                counter++;
                if (counter > max) {
                    max = counter;
                }
            } else {
                if (counter > max) {
                    max = counter;
                }
                counter = 0;
            }
        }
        return max;
    }
}
