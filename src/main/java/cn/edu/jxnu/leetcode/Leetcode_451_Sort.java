package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 梦境迷离
 * @description 桶排序
 *
 * 				按照字符出现次数对字符串排序
 *
 *              Input: "tree"
 *
 *              Output: "eert"
 *
 *              Explanation: 'e' appears twice while 'r' and 't' both appear
 *              once. So 'e' must appear before both 'r' and 't'. Therefore
 *              "eetr" is also a valid answer.
 * @time 2018年4月8日
 */
public class Leetcode_451_Sort {
    @SuppressWarnings("unchecked")
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        // 记录字符出现的次数
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // 桶
        List<Character>[] frequencyBucket = new List[s.length() + 1];
        for (char c : map.keySet()) {
            int f = map.get(c);
            if (frequencyBucket[f] == null) {
                frequencyBucket[f] = new ArrayList<>();
            }
            // 出现次数相同的放在同一个桶中，次数多的在后面
            frequencyBucket[f].add(c);
        }
        StringBuilder str = new StringBuilder();
        for (int i = frequencyBucket.length - 1; i >= 0; i--) {
            // 跳过空桶
            if (frequencyBucket[i] == null) {
                continue;
            }
            // 不为空，拼接为字符串
            for (char c : frequencyBucket[i]) {
                for (int j = 0; j < i; j++) {
                    str.append(c);
                }
            }
        }
        return str.toString();
    }

}
