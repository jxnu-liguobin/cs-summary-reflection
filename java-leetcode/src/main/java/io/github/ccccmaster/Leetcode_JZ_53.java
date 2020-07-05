/* All Contributors (C) 2020 */
package io.github.ccccmaster;

/**
 * 0～n-1中缺失的数字
 *
 * @author chenyu
 * @date 2020-07-05.
 */
public class Leetcode_JZ_53 {

    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i != nums[i]) {
                return i;
            }
        }
        return nums.length;
    }
}
