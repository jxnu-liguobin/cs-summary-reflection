package cn.edu.jxnu.leetcode.dp;

import java.util.List;

/**
 * 按平方数来分割整数
 * 
 * 279. Perfect Squares(Medium)
 * 
 * 题目描述：For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n =
 * 13, return 2 because 13 = 4 + 9.
 * 
 * 与343题类似
 * 
 * @author 梦境迷离.
 * @time 2018年6月20日
 * @version v1.0
 */
public class Leetcode_279_Dp {

	public static void main(String[] args) {
		int n = 17;
		List<Integer> squareList = generateSquareList(n);
		squareList.stream().forEach(System.out::print);
		System.out.println();
		Leetcode_279_Dp.numSquares(n);

	}

	public static int numSquares(int n) {
		List<Integer> squareList = generateSquareList(n);
		int[] dp = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			int min = Integer.MAX_VALUE;
			for (int square : squareList) {
				// 因为n可分解为某数的平方，则该数起码大于等于列表的第一个数【因为列表的数是有序的，小于号才可以找到最小且适合的那个平方数】
				if (i < square) {
					break;// i=2,3时满足此时，2>1,dp[2]=1+1,3>1,dp[3]=1+1+1,因为min默认重置为最大值，所以取min()
				}
				// dp的临时结果,计算第二个平方数时需要对前面的结果加1,i=4时得到第一个值，min重置为1即为临时结果
				min = Math.min(min, dp[i - square] + 1);
			}
			System.out.println(" i=" + i + ",dp[i]=min=" + min);
			dp[i] = min;// 得到临时结果，break之后直接执行这条语句，即若i=2,3时，则dp[i]的值与前一个相等。
		}
		return dp[n];
	}

	/**
	 * 算法， 返回小于12且为某个数的平方值的数
	 * 
	 * 类似对[1,根号n)进行求平方
	 *
	 */
	private static List<Integer> generateSquareList(int n) {
		List<Integer> squareList = new ArrayList<>();
		int diff = 3;
		int square = 1;
		while (square <= n) {
			squareList.add(square);
			square += diff;
			diff += 2;
		}
		return squareList;
	}
}
