/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

class MaximumXorOfTwoNumbersInAnArray {
    public int findMaximumXOR(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int xor = nums[i] ^ nums[j];
                if (xor > max) {
                    max = xor;
                }
            }
        }
        return max;
    }
}
