package io.github.ccccmaster;

/**
 * Offer_53_QueShiDeShuziLcof
 *
 * @author chenyu
 * @date 2020-07-05.
 */
public class Offer_53_QueShiDeShuziLcof {

    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i != nums[i]) {
                return i;
            }
        }
        return nums.length;
    }
}
