package cn.edu.jxnu.leetcode.dp;

import java.util.Arrays;

/**
 * 一组整数对能够构成的最长链
 * 
 * 646. Maximum Length of Pair Chain (Medium)
 * 
 * Input: [[1,2], [2,3], [3,4]]
 * 
 * Output: 2
 * 
 * Explanation: The longest chain is [1,2] -> [3,4]
 * 
 * 题目描述：对于 (a, b) 和 (c, d) ，如果 b < c，则它们可以构成一条链。
 * 
 * 
 * @author 梦境迷离.
 * @time 2018年6月9日
 * @version v1.0
 */
public class Leetcode_646_Dp {

	/**
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		int array[][] = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
		int ret = Leetcode_646_Dp.findLongestChain(array);
		System.out.println(ret);
	}

	public static int findLongestChain(int[][] pairs) {
		if (pairs == null || pairs.length == 0) {
			return 0;
		}
		Arrays.sort(pairs, (a, b) -> (a[0] - b[0])); // 对数组以第一列进行排序
		int n = pairs.length; // 对数
		int[] dp = new int[n];
		Arrays.fill(dp, 1);// 使用1填充数组
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				// j才是跑的慢的那个“指针”，j始终小于i，在i的前面，所以使用j的1，i的0位置
				if (pairs[i][0] > pairs[j][1]) {// 判断a[i][1]>a[i+1][0]
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
		}

		int ret = 0;
		for (int num : dp) {
			ret = Math.max(ret, num);
		}
		return ret;
	}
}
