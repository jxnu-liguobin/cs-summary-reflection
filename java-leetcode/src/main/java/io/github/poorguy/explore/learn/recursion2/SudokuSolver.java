/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

class SudokuSolver {
    private static final char[] DIGITS = "123456789".toCharArray();

    public void solveSudoku(char[][] board) {
        backtrace(board, 0, 0);
    }

    /** m row n column */
    private boolean backtrace(char[][] board, int m, int n) {
        if (board[m][n] != '.') {
            if (n < 8) {
                boolean t = backtrace(board, m, n + 1);
                return t;
            } else if (m < 8) {
                return backtrace(board, m + 1, 0);
            } else {
                return true;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (!isValid(board, DIGITS[i], m, n)) {
                continue;
            }

            board[m][n] = DIGITS[i];
            if (n < 8) {
                if (backtrace(board, m, n + 1)) {
                    return true;
                }
                board[m][n] = '.';
            } else if (m < 8) {
                if (backtrace(board, m + 1, 0)) {
                    return true;
                }
                board[m][n] = '.';
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(char[][] board, char val, int m, int n) {
        for (int i = 0; i < board.length; i++) {
            if (board[m][i] == val) return false;
            if (board[i][n] == val) return false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[m / 3 * 3 + i][n / 3 * 3 + j] == val) {
                    return false;
                }
            }
        }
        return true;
    }
}
