/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）， 找到所有被 'X' 围绕的区域。 并将区域里所有 'O'用 'X' 填充。
 * @time 2018年4月9日
 */
public class Leetcode_130_DFS {
    private int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int m, n;

    /**
     * @author 梦境迷离
     * @description 先填充最外侧，剩下的就是里侧了。 1、检查边界、有O则置为T 2、将所有其他的O置为X 3、将T置为O
     * @time 2018年4月9日
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        m = board.length;
        n = board[0].length;
        // 处理边界
        for (int i = 0; i < m; i++) {
            // 行上的，0列
            dfs(board, i, 0);
            // 最后一列
            dfs(board, i, n - 1);
        }
        for (int i = 0; i < n; i++) {
            // 列上的，0行
            dfs(board, 0, i);
            // 最后一行
            dfs(board, m - 1, i);
        }
        // 遍历、更改矩阵
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'T') {
                    // 将T还原为O
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    // 没有被置为T的O改为X
                    board[i][j] = 'X';
                }
            }
        }
    }

    /**
     * des更改矩阵
     *
     * @author 梦境迷离
     * @description
     * @time 2018年4月9日
     */
    private void dfs(char[][] board, int r, int c) {
        // 将不可修改的置为T
        if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') return;
        board[r][c] = 'T';
        // 四个方向
        for (int i = 0; i < direction.length; i++) {
            dfs(board, r + direction[i][0], c + direction[i][1]);
        }
    }
}
