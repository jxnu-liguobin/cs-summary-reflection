package io.github.dreamylost.dp;

import java.util.List;

/**
 * 字符串按单词列表分割
 *
 * <p>139. Word Break (Medium)
 *
 * <p>s = "leetcode", dict = ["leet", "code"]. Return true because "leetcode" can be segmented as
 * "leet code". 这是一个完全背包问题，和 0-1 背包不同的是，完全背包中物品可以使用多次。在这一题当中，词典中的单词可以被使用多次。
 *
 * <p>0-1 背包和完全背包在实现上的不同之处是，0-1 背包对物品的迭代是在最外层，而完全背包对物品的迭代是在最里层。
 *
 * @author 梦境迷离.
 * @time 2018年6月12日
 * @version v1.0
 */
public class Leetcode_139_Dp {

    public boolean workBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i < n; i++) { // / 每个单词可以使用多次,元素循环在内层
            for (String word : wordDict) {
                int len = word.length();
                if (len <= i && word.equals(s.substring(i - len, i))) {
                    dp[i] = dp[i] || dp[i - len];
                }
            }
        }
        return dp[n];
    }
}
