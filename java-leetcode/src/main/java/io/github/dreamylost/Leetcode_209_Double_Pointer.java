/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 209. 长度最小的子数组
 *
 * <p>给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
 *
 * @author 梦境迷离
 * @time 2018-09-25
 */
public class Leetcode_209_Double_Pointer {

    public int minSubArrayLen(int sum, int[] nums) {
        int minlen = Integer.MAX_VALUE;
        int curSum = 0;
        int start = 0;
        int end = 0;
        while (start < nums.length) {
            if (curSum < sum && end < nums.length) {
                curSum += nums[end];
                end++;
            } else if (curSum >= sum) {
                minlen = Math.min(minlen, end - start);
                curSum -= nums[start];
                start++;
            } else {
                break;
            }
        }
        return (minlen == Integer.MAX_VALUE) ? 0 : minlen;
    }
}
