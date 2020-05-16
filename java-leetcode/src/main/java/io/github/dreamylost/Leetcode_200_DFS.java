package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 给定 '1'（陆地）和 '0'（水）的二维网格图，计算岛屿的数量。一个岛被水包围，并且通过水平或垂直连接相邻的陆地而形成。 你可以假设网格的四个边均被水包围。
 *     <p>示例 1:
 *     <p>11110 11010 11000 00000 答案: 1
 *     <p>示例 2:
 *     <p>11000 11000 00100 00011 答案: 3
 *     <p>贡献者:
 * @time 2018年4月8日
 */
public class Leetcode_200_DFS {
    private int m, n;
    private int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;
        int ret = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    ret++;
                }
            }
        }
        return ret;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == '0') return;
        grid[i][j] = '0';
        // 循环四次，四个方向
        for (int k = 0; k < direction.length; k++) {
            dfs(grid, i + direction[k][0], j + direction[k][1]);
        }
    }
}
