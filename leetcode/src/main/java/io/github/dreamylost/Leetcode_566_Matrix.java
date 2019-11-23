package io.github.dreamylost;

/**
 * 改变矩阵维度
 * 
 * 566. Reshape the Matrix (Easy)
 * 
 * Input: nums = [[1,2], [3,4]] r = 1, c = 4
 * 
 * Output: [[1,2,3,4]]
 * 
 * Explanation: The row-traversing of nums is [1,2,3,4]. The new reshaped matrix
 * is a 1 * 4 matrix, fill it row by row by using the previou
 * 
 * @author 梦境迷离.
 * @time 2018年7月13日
 * @version v1.0
 */
public class Leetcode_566_Matrix {

	public int[][] matrixReshape(int[][] nums, int r, int c) {
		int m = nums.length, n = nums[0].length;
		if (m * n != r * c) {
			return nums;// 不能转化直接返回即可
		}
		int[][] reshapedNums = new int[r][c];// 能转化
		int index = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				reshapedNums[i][j] = nums[index / n][index % n];
				index++;
			}
		}
		return reshapedNums;
	}

	/**
	 * low方法
	 *
	 */
	public int[][] matrixReshape2(int[][] nums, int r, int c) {
		int n = nums.length;
		int m = nums[0].length;
		int arr[][] = new int[r][c];
		if (m * n == r * c) {
			int[] num = new int[n * m];
			for (int i = 0, k = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					num[k] = nums[i][j];
					k++;
				}
			}
			for (int i = 0, k = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					arr[i][j] = num[k];
					k++;
				}
			}
			return arr;
		} else
			return nums;
	}
}
