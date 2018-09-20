package cn.edu.jxnu.leetcode;

/**
 * @author 梦境迷离
 * @description 题目描述：第 i 行摆 i 个，统计能够排列的行数。 返回 h 而不是 l，因为摆的硬币最后一行不能算进去。
 * @time 2018年3月30日
 */
public class Leetcode_441 {
	/**
	 * 
	 * @author 梦境迷离
	 * @description 二分法查询
	 * @time 2018年3月30日
	 */
	public int arrangeCoins(int n) {
		int l = 0, h = n;
		while (l <= h) {
			// 等差数列，1+2+3+...k = n, 至多是n/2层
			int m = l + (h - l) / 2;
			// 从n/2处开始逼近
			long x = m * (m + 1L) / 2;
			if (x == n)
				return m;
			else if (x < n)
				l = m + 1;
			else
				h = m - 1;
		}
		return h;
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 更直观的解法
	 * @time 2018年3月30日
	 */
	public int arrangeCoins2(int n) {
		int level = 1;
		while (n > 0) {
			n -= level;
			level++;
		}
		// n==0,刚好铺满最后一层
		return n == 0 ? level - 1 : level - 2;
	}
}
