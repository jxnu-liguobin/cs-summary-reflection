package cn.edu.jxnu.leetcode;

/**
 * 一个数组元素在 [1, n] 之间，其中一个数被替换为另一个数，找出重复的数和丢失的数
 * 
 * 645. Set Mismatch (Easy) Input: nums = [1,2,2,4] Output: [2,3]
 * 
 * @author 梦境迷离.
 * @time 2018年7月14日
 * @version v1.0
 */
public class Leetcode_645_Array {

	/**
	 * 最直接的方法是先对数组进行排序，这种方法时间复杂度为 O(NlogN)。本题可以以 O(N) 的时间复杂度、O(1) 空间复杂度来求解。
	 * 主要思想是通过交换数组元素，使得数组上的元素在正确的位置上。
	 * 
	 * n个元素的数组nums[i]的值在 i+1~n
	 * 
	 * 遍历数组，如果第 i 位上的元素不是 i + 1，那么一直交换第 i 位和 nums[i] - 1 位置上的元素。
	 */
	public int[] findErrorNums(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
				swap(nums, i, nums[i] - 1);
			}
		}
		// 1,2,2,4返回2，3
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != i + 1) {
				return new int[] { nums[i], i + 1 };
			}
		}
		return null;
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
