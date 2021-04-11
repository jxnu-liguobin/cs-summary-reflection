/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

class TargetSum {
    public int findTargetSumWays(int[] nums, int target) {
        return dfs(nums, 0, 0, target);
    }

    private int dfs(int[] nums, int index, int sum, int target) {
        if (index == nums.length - 1) {
            int minus = -nums[index] + sum == target ? 1 : 0;
            int add = nums[index] + sum == target ? 1 : 0;
            return minus + add;
        }

        int minus = dfs(nums, index + 1, sum - nums[index], target);
        int add = dfs(nums, index + 1, sum + nums[index], target);
        return minus + add;
    }
}
