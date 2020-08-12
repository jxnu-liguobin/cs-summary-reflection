/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * 被围绕的区域
 *
 * <p>使用回溯即可
 *
 * @author kongwiki@163.com
 * @since 2020/8/11上午9:18
 */
public class SurroundedRegions {
    public void solve(char[][] board) {
        // 思路:
        // 正常思路: 寻找被包围的O, 然后改为X即可, 但是无法判断O是否被包围
        // 逆向思路: 寻找和边界的O相邻的O, 这些明显是无法被包围的, 其余的O改为X即可
        // 方法: 回溯, 剑指Offer: 机器人运动路径
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int row = board.length;
        int col = board[0].length;

        // 步骤1: 寻找和边界O相邻的O
        // 0列+col-1列
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') {
                // 回溯 +　标记
                backtrack(board, i, 0, row, col);
            }
            if (board[i][col - 1] == 'O') {
                backtrack(board, i, col - 1, row, col);
            }
        }

        // O行 + row-1行
        for (int j = 0; j < col; j++) {
            if (board[0][j] == 'O') {
                // 回溯+标记
                backtrack(board, 0, j, row, col);
            }
            if (board[row - 1][j] == 'O') {
                backtrack(board, row - 1, j, row, col);
            }
        }

        // 步骤2: 填充O
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

        // 步骤3: 复原更改的O
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void backtrack(char[][] board, int i, int j, int row, int col) {
        if (i < 0 || i >= row || j < 0 || j >= col || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'A';
        backtrack(board, i + 1, j, row, col);
        backtrack(board, i - 1, j, row, col);
        backtrack(board, i, j + 1, row, col);
        backtrack(board, i, j - 1, row, col);
    }
}
