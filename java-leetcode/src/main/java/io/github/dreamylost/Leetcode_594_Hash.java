package io.github.dreamylost;

import java.util.HashMap;
import java.util.Map;

/**
 * 最长和谐序列
 *
 * <p>594. Longest Harmonious Subsequence (Easy)
 *
 * <p>Input: [1,3,2,2,5,2,3,7] Output: 5 Explanation: The longest harmonious subsequence is
 * [3,2,2,2,3]. 和谐序列中最大数和最小数只差正好为 1，应该注意的是序列的元素不一定是数组的连续元素。
 *
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_594_Hash {

    public int findLHS(int[] nums) {
        Map<Integer, Integer> countForNum = new HashMap<>();
        for (int num : nums) {
            countForNum.put(num, countForNum.getOrDefault(num, 0) + 1);
        }
        int longest = 0;
        for (int num : countForNum.keySet()) {
            if (countForNum.containsKey(num + 1)) {
                longest = Math.max(longest, countForNum.get(num + 1) + countForNum.get(num));
            }
        }
        return longest;
    }
}
