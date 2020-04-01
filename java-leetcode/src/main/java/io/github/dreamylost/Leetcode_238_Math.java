package io.github.dreamylost;

import java.util.Arrays;

/**
 * 乘积数组
 * 
 * 238. Product of Array Except Self (Medium)
 * 
 * For example, given [1,2,3,4], return [24,12,8,6].
 * 给定一个数组，创建一个新数组，新数组的每个元素为原始数组中除了该位置上的元素之外所有元素的乘积。
 * 
 * 要求时间复杂度为 O(N)，并且不能使用除法。
 * 
 * @author 梦境迷离.
 * @time 2018年6月28日
 * @version v1.0
 */
public class Leetcode_238_Math {
	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4 };
		int[] ret = Leetcode_238_Math.productExceptSelf(arr);// 基本类型不能使用asList，且数组无法collect拼接
		Arrays.stream(ret).forEach(x -> System.out.print(x));
	}

	public static int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] products = new int[n];
		Arrays.fill(products, 1);// 填充数组
		int left = 1;
		int right = 1;
		// /由于num[0]之前或num[Length-1]之后没有数字，所以将乘积之前的left和之后right设置为1。
		for (int i = 0; i < nums.length; i++) {
			int j = nums.length - i - 1;
			// nums[i]左边的乘积
			products[i] *= left;
			left *= nums[i];
			// nums[i]右边的乘积
			products[j] *= right;
			right *= nums[j];
		}
		return products;

	}
}
