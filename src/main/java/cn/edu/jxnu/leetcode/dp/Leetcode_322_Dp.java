package cn.edu.jxnu.leetcode.dp;

import java.util.Arrays;

/**
 * 找零钱的方法数
	
	322. Coin Change (Medium)
	
	Example 1:
	coins = [1, 2, 5], amount = 11
	return 3 (11 = 5 + 5 + 1)
	
	Example 2:
	coins = [2], amount = 3
	return -1.
	题目描述：给一些面额的硬币，要求用这些硬币来组成给定面额的钱数，并且使得硬币数量最少。硬币可以重复使用。
	
	物品：硬币
	物品大小：面额
	物品价值：数量
	因为硬币可以重复使用，因此这是一个完全背包问题。

 * @author 梦境迷离.
 * @time 2018年6月16日
 * @version v1.0
 */
public class Leetcode_322_Dp {
	
	public static void main(String[] args) {
		int coins[] = { 1, 2, 5 };
		int amount = 11;
		int ret = Leetcode_322_Dp.coinChange(coins, amount);
		System.out.println(ret);
	}

	public static int coinChange(int[] coins, int amount) {
		if (coins == null || coins.length == 0) {
			return 0;
		}
		int[] minimum = new int[amount + 1];
		Arrays.fill(minimum, amount + 1);
		minimum[0] = 0;
		Arrays.sort(coins);
		for (int i = 1; i <= amount; i++) {// 可以重复使用，所以面值放在外面，币值放在里面
			for (int j = 0; j < coins.length && coins[j] <= i; j++) {
				minimum[i] = Math.min(minimum[i], minimum[i - coins[j]] + 1); // 币数量是需要最小
			}
		}
		return minimum[amount] > amount ? -1 : minimum[amount];
	}
}
