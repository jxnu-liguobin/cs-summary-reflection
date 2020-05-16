package io.github.dreamylost.dp;

import java.util.Arrays;

/**
 * 组合总和
 *
 * <p>377. Combination Sum IV (Medium)
 *
 * <p>nums = [1, 2, 3] target = 4
 *
 * <p>The possible combination ways are: (1, 1, 1, 1) (1, 1, 2) (1, 2, 1) (1, 3) (2, 1, 1) (2, 2)
 * (3, 1)
 *
 * <p>Note that different sequences are counted as different combinations.
 *
 * <p>Therefore the output is 7.
 *
 * @author 梦境迷离.
 * @time 2018年6月17日
 * @version v1.0
 */
public class Leetcode_377_Dp {

    /** 完全背包。 */
    public int combinationSum4(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] maximum = new int[target + 1];
        maximum[0] = 1;
        Arrays.sort(nums); // 排序
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length && nums[j] <= i; j++) { // 物品可以重复，放里面
                maximum[i] += maximum[i - nums[j]];
            }
        }
        return maximum[target];
    }
}
