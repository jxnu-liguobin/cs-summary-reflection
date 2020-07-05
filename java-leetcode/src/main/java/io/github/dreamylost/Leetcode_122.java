/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 假设有一个数组，它的第 i 个元素是一个给定的股票在第 i 天的价格。
 *     设计一个算法来找到最大的利润。你可以完成尽可能多的交易（多次买卖股票）。然而，你不能同时参与多个交易（你必须在再次购买前出售股票 ）。
 * @time 2018年3月31日
 */
/**
 * @author 梦境迷离
 * @description 对于一个交易 [a, b, c, d]，如果有 a <= b <= c <= d ，那么最大收益为 d - a = (d - c) + (c - b) + (b -
 *     a) 。 当访问到一个 prices[i] 且 prices[i] - prices[i-1] ，那么就把 prices[i] - prices[i-1]
 *     加到收益中，从而在局部最优的情况下也保证全局最优。
 * @time 2018年3月31日
 */
public class Leetcode_122 {
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) profit += (prices[i] - prices[i - 1]);
        }
        return profit;
    }
}
