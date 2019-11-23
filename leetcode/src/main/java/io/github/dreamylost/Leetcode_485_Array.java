package io.github.dreamylost;

/**
 * 找出数组中最长的连续 1
 * 
 * 485. Max Consecutive Ones (Easy)
 * 
 * @author 梦境迷离.
 * @time 2018年7月14日
 * @version v1.0
 */
public class Leetcode_485_Array {

	public int findMaxConsecutiveOnes(int[] nums) {
		int max = 0, cur = 0;
		for (int x : nums) {
			cur = x == 0 ? 0 : cur + 1;
			max = Math.max(max, cur);
		}
		return max;
	}
}
