package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 给定一个长度为 n 的整数数组，你的任务是判断在最多改变 1 个元素的情况下，该数组能否变成一个非递减数列。 我们是这样定义一个非递减数列的： 对于数组中所有的 i
 *     (1 <= i < n)，满足 array[i] <= array[i + 1]。 示例 1: 输入: [4,2,3] 输出: True 解释:
 *     你可以通过把第一个4变成1来使得它成为一个非递减数列。 示例 2: 输入: [4,2,1] 输出: False 解释: 你不能在只改变一个元素的情况下将其变为非递减数列。 说明: n
 *     的范围为 [1, 10,000]。
 *     <p>在 nums[i] < nums[i - 1] 的情况下，会优先考虑令 nums[i - 1] = nums[i]，因为如果修改 nums[i] = nums[i - 1] 的话，
 *     那么 nums[i] 这个数会变大，那么就有可能比 nums[i + 1] 大，我们要尽量使 nums[i] 更小。但是在 nums[i] < nums[i - 2] 的情况下， 只修改
 *     nums[i - 1] 不能令数组成为非递减，只能通过修改 nums[i] = nums[i - 1] 才行。
 * @time 2018年4月1日
 */
public class Leetcode_665 {

    public boolean checkPossibility(int[] nums) {
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                cnt++;
                // 前二2个大于当前这个元素，则不能简单的将前一个修改为当前元素
                // 应该将当前元素改为前一个元素
                if (i - 2 > 0 && nums[i - 2] > nums[i]) {
                    nums[i] = nums[i - 1];
                } else {
                    // 前一个改成当前元素
                    nums[i - 1] = nums[i];
                }
            }
        }
        return cnt <= 1;
    }
}
