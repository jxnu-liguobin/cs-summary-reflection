/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.*;

/**
 * 题目: 332. 重新安排行程
 *
 * <p>给定一个机票的字符串二维数组 [from, to]，
 *
 * <p>子数组中的两个成员分别表示飞机出发和降落的机场地点，
 *
 * <p>对该行程进行重新规划排序。
 *
 * <p>所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，
 *
 * <p>所以该行程必须从 JFK 开始。
 *
 * @author kongwiki@163.com
 * @since 2020/8/27上午10:04
 */
public class ReconstructItinerary {
    private String start = "JFK";
    final Map<String, PriorityQueue<String>> map = new HashMap<>();
    final List<String> itinerary = new ArrayList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            if (!map.containsKey(src)) {
                map.put(src, new PriorityQueue<>());
            }
            map.get(src).offer(dst);
        }
        dfs(start);
        Collections.reverse(itinerary);
        return itinerary;
    }

    public void dfs(String curr) {
        while (map.containsKey(curr) && map.get(curr).size() > 0) {
            String tmp = map.get(curr).poll();
            dfs(tmp);
        }
        itinerary.add(curr);
    }
}
