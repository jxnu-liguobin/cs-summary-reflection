/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

class SingleNumber {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
