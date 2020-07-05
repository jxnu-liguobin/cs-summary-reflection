/* All Contributors (C) 2020 */
package io.github.dreamylost.dp;

/**
 * 买入和售出股票最大的收益
 *
 * <p>121. Best Time to Buy and Sell Stock (Easy)
 *
 * <p>题目描述：只进行一次交易。
 *
 * <p>只要记录前面的最小价格，将这个最小价格作为买入价格，然后将当前的价格作为售出价格，查看当前收益是不是最大收益。
 *
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_121_Dp {

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int soFarMin = prices[0];
        int max = 0;
        for (int i = 1; i < n; i++) {
            if (soFarMin > prices[i]) soFarMin = prices[i];
            else max = Math.max(max, prices[i] - soFarMin);
        }
        return max;
    }
}
