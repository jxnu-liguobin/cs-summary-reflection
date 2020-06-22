/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.ArrayList;
import java.util.List;

/**
 * Pacific Atlantic Water Flow (Medium)
 *
 * <p>Given the following 5x5 matrix:
 *
 * <p>Pacific ~ ~ ~ ~ ~ ~ 1 2 2 3 (5) * ~ 3 2 3 (4) (4) * ~ 2 4 (5) 3 1 * ~ (6) (7) 1 4 5 * ~ (5) 1
 * 1 2 4 * * * * * Atlantic Return: [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
 * (positions with parentheses in above matrix).
 * 题目描述：左边和上边是太平洋，右边和下边是大西洋，内部的数字代表海拔，海拔高的地方的水能够流到低的地方，求解水能够流到太平洋和大西洋的所有位置。
 *
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月12日
 */
public class Leetcode_417_DFS {
    private int m, n;
    private int[][] matrix;
    private int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> ret = new ArrayList<int[]>();
        if (matrix == null || matrix.length == 0) return ret;
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.matrix = matrix;
        /** 默认boolean数组，标记水是否可以流向 */
        boolean[][] canReachP = new boolean[m][n];
        boolean[][] canReachA = new boolean[m][n];
        /** 对边界进行四个方向上的dfs遍历 */
        for (int i = 0; i < m; i++) {
            // 在行上对第1列和最后1列进行遍历
            dfs(i, 0, canReachP);
            dfs(i, n - 1, canReachA);
        }
        for (int i = 0; i < n; i++) {
            // 在列上对第1行和最后1行进行遍历
            dfs(0, i, canReachP);
            dfs(m - 1, i, canReachA);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (canReachP[i][j] && canReachA[i][j]) {
                    ret.add(new int[] {i, j});
                }
            }
        }
        return ret;
    }

    /**
     * 图的深度优先遍历
     *
     * @time. 下午8:23:31
     * @version V1.0
     * @param row
     * @param col
     * @param canReach
     */
    public void dfs(int row, int col, boolean[][] canReach) {
        if (canReach[row][col]) return;
        canReach[row][col] = true;
        for (int i = 0; i < direction.length; i++) {
            int nextRow = direction[i][0] + row;
            int nextCol = direction[i][1] + col;
            // 必须是大于等于的，才可以满足水流向大海
            if (nextRow < 0
                    || nextRow >= m
                    || nextCol < 0
                    || nextCol >= n
                    || matrix[row][col] > matrix[nextRow][nextCol]) {
                continue;
            }
            dfs(nextRow, nextCol, canReach);
        }
    }
}
