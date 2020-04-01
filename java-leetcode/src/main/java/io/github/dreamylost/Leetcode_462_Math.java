package io.github.dreamylost;

import java.util.Arrays;

/**
	 改变数组元素使所有的数组元素都相等
	
	462. Minimum Moves to Equal Array Elements II (Medium)
	
	Input:
	[1,2,3]
	
	Output:
	2
	
	Explanation:
	Only two moves are needed (remember each move increments or decrements one element):
	
	[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
	每次可以对一个数组元素加一或者减一，求最小的改变次数。
	
	这是个典型的相遇问题，移动距离最小的方式是所有元素都移动到中位数。理由如下：
	
	设 m 为中位数。a 和 b 是 m 两边的两个元素，且 b > a。要使 a 和 b 相等，它们总共移动的次数为 b - a，
	这个值等于 (b - m) + (m - a)，也就是把这两个数移动到中位数的移动次数。
	
	设数组长度为 N，则可以找到 N/2 对 a 和 b 的组合，使它们都移动到 m 的位置。
	
 * @author 梦境迷离.
 * @time 2018年6月23日
 * @version v1.0
 */
public class Leetcode_462_Math {

	/*
	 * 解法 1 先排序，时间复杂度：O(NlogN)
	 */
	public int minMoves2(int[] nums) {
		Arrays.sort(nums);
		int move = 0;
		int l = 0, h = nums.length - 1;
		while (l <= h) {
			move += nums[h] - nums[l];
			l++;
			h--;
		}
		return move;
	}

	/*
	 * 解法 2
	 * 
	 * 使用快速选择找到中位数，时间复杂度 O(N)
	 */
	public int minMoves(int[] nums) {
		int move = 0;
		int median = findKthSmallest(nums, nums.length / 2);
		for (int num : nums) {
			move += Math.abs(num - median);
		}
		return move;
	}

	private int findKthSmallest(int[] nums, int k) {
		int l = 0, h = nums.length - 1;
		while (l < h) {
			int j = partition(nums, l, h);
			if (j == k) {
				break;
			}
			if (j < k) {
				l = j + 1;
			} else {
				h = j - 1;
			}
		}
		return nums[k];
	}

	private int partition(int[] nums, int l, int h) {
		int i = l, j = h + 1;
		while (true) {
			while (nums[++i] < nums[l] && i < h)
				;
			while (nums[--j] > nums[l] && j > l)
				;
			if (i >= j) {
				break;
			}
			swap(nums, i, j);
		}
		swap(nums, l, j);
		return j;
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
