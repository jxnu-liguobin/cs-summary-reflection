/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.dp;

/**
 * 只能进行 k 次的股票交易
 *
 * <p>一买一卖记为一次 188. Best Time to Buy and Sell Stock IV (Hard)
 *
 * @author 梦境迷离.
 * @time 2018年6月17日
 * @version v1.0
 */
public class Leetcode_188_Dp {

    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        // 你不能同时拥有两份股票。也就是说在下次买入前，你必须把手头上原有的股票先卖掉。
        if (k >= n / 2) { // 这种情况下该问题退化为普通的股票交易问题
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                // 只要比前手大就是赚钱，增加利润值
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }
        int[][] maxProfit = new int[k + 1][n];
        for (int i = 1; i <= k; i++) { // 背包容量
            int localMax = maxProfit[i - 1][0] - prices[0];
            for (int j = 1; j < n; j++) { // 背包物品
                maxProfit[i][j] =
                        Math.max(maxProfit[i][j - 1], prices[j] + localMax); // 前i次，不超过j的股票最大收益
                localMax = Math.max(localMax, maxProfit[i - 1][j] - prices[j]);
            }
        }
        return maxProfit[k][n - 1];
    }
}
