package io.github.dreamylost.dp;

/**
 * 只能进行两次的股票交易
 *
 * <p>123. Best Time to Buy and Sell Stock III (Hard)
 *
 * @author 梦境迷离.
 * @time 2018年6月17日
 * @version v1.0
 */
public class Leetcode_123_Dp {

    public int maxProfit(int[] prices) {
        int firstBuy = Integer.MIN_VALUE, firstSell = 0;
        int secondBuy = Integer.MIN_VALUE, secondSell = 0;
        for (int curPrice : prices) {
            if (firstBuy < -curPrice) {
                firstBuy = -curPrice;
            }
            if (firstSell < firstBuy + curPrice) {
                firstSell = firstBuy + curPrice;
            }
            if (secondBuy < firstSell - curPrice) {
                secondBuy = firstSell - curPrice;
            }
            if (secondSell < secondBuy + curPrice) {
                secondSell = secondBuy + curPrice;
            }
        }
        return secondSell;
    }

    /** 买入，卖出，不能买两次再一起卖出 */
    public int maxProfit2(int[] prices) {
        int sell1 = 0, sell2 = 0, buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]); // -999，-p，即第一次买入需要花费p
            sell1 = Math.max(sell1, buy1 + prices[i]); // 0，买入价格+当前价格 //买入是付的，此后这个方程叠加，是买卖将得到利润
            buy2 = Math.max(buy2, sell1 - prices[i]); // 第二次买，花费
            sell2 = Math.max(sell2, buy2 + prices[i]); // 第二次卖，买入+当前价格，同第一次
        }
        return sell2;
    }
}
