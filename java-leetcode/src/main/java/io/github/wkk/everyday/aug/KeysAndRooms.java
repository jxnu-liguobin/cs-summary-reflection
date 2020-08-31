/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.*;

/**
 * 题目：841.钥匙和房间
 *
 * <p>描述： 有 N 个房间，开始时你位于 0 号房间。 每个房间有不同的号码：0，1，2，...，N-1， 并且房间里可能有一些钥匙能使你进入下一个房间。
 *
 * <p>示例： 输入: [[1],[2],[3],[]] 输出: true 解释: 我们从 0 号房间开始，拿到钥匙 1。 之后我们去 1 号房间，拿到钥匙 2。 然后我们去 2 号房间，拿到钥匙
 * 3。 最后我们去了 3 号房间。 由于我们能够进入每个房间，我们返回 true。
 *
 * <p>示例 2： 输入：[[1,3],[3,0,1],[2],[0]] 输出：false 解释：我们不能进入 2 号房间。
 *
 * <p>思路：又是图的遍历， 使用dfs，bfs均可
 *
 * @author kongwiki@163.com
 * @since 2020/8/31 1:13 下午
 */
public class KeysAndRooms {
    private static boolean[] visited;
    private static int num;

    /** dfs */
    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        num = 0;
        visited = new boolean[n];
        dfs(rooms, 0);
        return num == n;
    }

    private static void dfs(List<List<Integer>> rooms, int start) {
        visited[start] = true;
        num++;
        for (Integer integer : rooms.get(start)) {
            if (!visited[integer]) {
                dfs(rooms, integer);
            }
        }
    }

    /** bfs */
    private static boolean bfs(List<List<Integer>> rooms) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        boolean[] visited = new boolean[rooms.size()];
        visited[0] = true;
        int num = 0;
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            num++;
            for (Integer integer : rooms.get(poll)) {
                if (!visited[integer]) {
                    visited[integer] = true;
                    queue.offer(integer);
                }
            }
        }
        return num == rooms.size();
    }

    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1));
        list.add(Arrays.asList(2));
        list.add(Arrays.asList(3));
        list.add(Arrays.asList());
        boolean b = canVisitAllRooms(list);
        System.out.println(b);
    }
}
