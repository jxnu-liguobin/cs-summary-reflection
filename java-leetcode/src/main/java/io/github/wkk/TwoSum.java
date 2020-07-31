/* All Contributors (C) 2020 */
package io.github.wkk;

import java.util.HashMap;

/**
 * @since 2020/7/30上午9:18
 * @author kongwiki kongwiki@163.com
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = hashMap.get(target - nums[i]);
                break;
            }
            hashMap.put(nums[i], i);
        }
        return res;
    }
}
