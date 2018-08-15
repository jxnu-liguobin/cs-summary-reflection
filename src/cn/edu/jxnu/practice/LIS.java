package cn.edu.jxnu.practice;


/**
 * <p>
 * Title: LIS.java
 * </p>
 * <p>
 * Description: 求数组最长递增子序列长度
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * School: jxnu
 * </p>
 * @require 时间复杂度尽可能低
 * @author Mr.Li
 * @date 2018-2-16
 * @version 1.0
 */
public class LIS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = { 1, -1, 2, -3, 4, -5, 6, -7 };
		int result = new LIS().lis(array);
		System.out.println("最大递增子序列长度是：" + result);

	}

	/**
	 * @description 动态规划 基础
	 * @param array
	 * @return
	 */
	public int lis(int[] array) {
		int[] lis = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			lis[i] = 1;
			for (int j = 0; j < i; j++) {
				if (array[i] > array[j] && lis[j] + 1 > lis[i]) {
					lis[i] = lis[j] + 1;
				}
			}
		}
		// 取得lis最大值
		int max = -1;
		for (int i = 0; i < lis.length; i++) {
			if (lis[i] > max) {
				max = lis[i];
			}
		}

		return max;
	}
}
