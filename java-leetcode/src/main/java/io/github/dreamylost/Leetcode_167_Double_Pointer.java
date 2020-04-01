package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。 从一个已经排序的数组中查找出两个数，使它们的和为 0
 * @time 2018年4月6日
 */
public class Leetcode_167_Double_Pointer {

	public int[] twoSum(int[] numbers, int target) {
		int i = 0, j = numbers.length - 1;
		while (i < j) {
			int sum = numbers[i] + numbers[j];
			// 如果两个指针指向元素的和 sum == target，那么得到要求的结果
			if (sum == target)
				return new int[] { i + 1, j + 1 };
			else if (sum < target)
				// 如果 sum < target，移动较小的元素，使 sum 变大一些。
				i++;
			else
				// 如果 sum > target，移动较大的元素，使 sum 变小一些
				j--;
		}
		return null;
	}
}
