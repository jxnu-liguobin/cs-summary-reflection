/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 梦境迷离
 * @description 广度优先搜索的搜索过程有点像一层一层地进行遍历，每层遍历都以上一层遍历的结果作为起点，遍历一个长度。需要注意的是， 遍历过的节点不能再次被遍历。
 *     <p>设 di 表示第 i 个节点与根节点的路径长度，推导出一个结论：对于先遍历的节点 i 与后遍历的节点 j，有 di<=dj。利用这个结论，可以求解最短路径等 最优解
 *     问题：第一次遍历到目的节点，其所经过的路径为最短路径，如果继续遍历，之后再遍历到目的节点，所经过的路径就不是最短路径。
 *     <p>在程序实现 BFS 时需要考虑以下问题：
 *     <p>队列：用来存储每一轮遍历的节点；
 *     <p>标记：对于遍历过的节点，应该将它标记，防止重复遍历。
 *     <p>计算在网格中从原点到特定点的最短路径长度
 *     <p>[[1,1,0,1], [1,0,1,0], [1,1,1,1], [1,0,1,1]]//图的邻接矩阵表示
 *     <p>1 表示可以经过某个位置。
 * @time 2018年4月8日
 */
public class BFS {
    public int minPathLength(int[][] grids, int tr, int tc) {
        // 表示四个方向的顶点
        int[][] next = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int m = grids.length, n = grids[0].length;
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(0, 0, 1));
        while (!queue.isEmpty()) {
            // 取出队首元素
            Position pos = queue.poll();
            // 循环四次，四个方向
            for (int i = 0; i < 4; i++) {
                // 新的行,新的列
                Position nextPos =
                        new Position(pos.r + next[i][0], pos.c + next[i][1], pos.length + 1);
                // 如果新的行和列超出范围就跳过这次循环
                if (nextPos.r < 0 || nextPos.r >= m || nextPos.c < 0 || nextPos.c >= n) {
                    continue;
                }
                // 已经被访问
                if (grids[nextPos.r][nextPos.c] != 1) {
                    continue;
                }
                // 标记当前节点已被访问
                grids[nextPos.r][nextPos.c] = 0;
                // 得到结果
                if (nextPos.r == tr && nextPos.c == tc) {
                    return nextPos.length;
                }
                // 添加到队列
                queue.add(nextPos);
            }
        }
        return -1;
    }

    private class Position {
        int r, c, length;

        public Position(int r, int c, int length) {
            this.r = r;
            this.c = c;
            this.length = length;
        }
    }
}
