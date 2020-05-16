package io.github.dreamylost.dp;

/**
 * 198. House Robber (Easy)
 *
 * <p>题目描述：抢劫一排住户，但是不能抢邻近的住户，求最大抢劫量。
 *
 * <p>定义 dp 数组用来存储最大的抢劫量，其中 dp[i] 表示抢到第 i 个住户时的最大抢劫量。由于不能抢劫邻近住户，因此如果抢劫了第 i 个住户那么只能抢劫 i - 2 和 i - 3
 * 的住户，所以
 *
 * <p>dp方程：dp[i]=max(dp[i-2],dp[i-3]) +nums[i]
 *
 * @author 梦境迷离.
 * @time 2018年6月7日
 * @version v1.0
 */
public class Leetcode_198_Dp {

    /** @param args */
    public static void main(String[] args) {
        int[] n = {1, 2, 3, 4};
        int s = new Leetcode_198_Dp().rob(n);
        System.out.println(s);
    }

    /**
     * O(n) 空间复杂度实现方法：
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = nums[1];
        dp[2] = nums[0] + nums[2];
        for (int i = 3; i < n; i++) {
            dp[i] = Math.max(dp[i - 2], dp[i - 3]) + nums[i];
        }
        return Math.max(dp[n - 1], dp[n - 2]);
    }

    /**
     * O(1) 空间复杂度实现方法：
     *
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);
        int pre3 = nums[0], pre2 = nums[1], pre1 = nums[2] + nums[0];
        for (int i = 3; i < n; i++) {
            int cur = Math.max(pre2, pre3) + nums[i];
            pre3 = pre2;
            pre2 = pre1;
            pre1 = cur;
        }
        return Math.max(pre1, pre2);
    }
}
