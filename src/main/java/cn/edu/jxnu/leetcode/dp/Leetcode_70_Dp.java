package cn.edu.jxnu.leetcode.dp;

/**
 * 
 * 递归和动态规划都是将原问题拆成多个子问题然后求解，他们之间最本质的区别是，动态规划保存了子问题的解，避免重复计算。
 * 
 * 70. Climbing Stairs (Easy)
 * 
 * 题目描述：有 N 阶楼梯，每次可以上一阶或者两阶，求有多少种上楼梯的方法。 定义一个数组 dp 存储上楼梯的方法数（为了方便讨论，数组下标从
 * 1开始），dp[i] 表示走到第 i 个楼梯的方法数目。 第 i 个楼梯可以从第 i-1 和 i-2 个楼梯再走一步到达，走到第 i
 * 个楼梯的方法数为走到第i-1 和第 i-2 个楼梯的方法数之和。dp[N] 即为所求。 考虑到 dp[i] 只与 dp[i - 1] 和 dp[i -
 * 2]
 * 
 * dp方程：dp[i]=dp[i-1]+dp[i-2]
 * 
 * 有关，因此可以只用两个变量来存储 dp[i - 1] 和 dp[i - 2]，使得原来的 O(N) 空间复杂度优化为 O(1) 复杂度。
 * 
 * @author 梦境迷离.
 * @time 2018年6月7日
 * @version v1.0
 */
public class Leetcode_70_Dp {

	/**
	 * @param args
	 *
	 */
	public static void main(String[] args) {

		int n = 3;
		int s = new Leetcode_70_Dp().climbStairs(n);
		System.out.println(s);
	}

	public int climbStairs(int n) {
		if (n <= 2)
			return n;
		int pre2 = 1, pre1 = 2;
		for (int i = 2; i < n; i++) {
			int cur = pre1 + pre2;
			pre2 = pre1;
			pre1 = cur;
		}
		return pre1;
	}

}
