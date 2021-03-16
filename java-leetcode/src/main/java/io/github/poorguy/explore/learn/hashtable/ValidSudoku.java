/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        return checkBlock(board) && checkLine(board) && checkColumn(board);
    }

    private boolean checkBlock(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean[] filled = new boolean[9];
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 3; n++) {
                        char c = board[i * 3 + m][j * 3 + n];
                        if (c != '.' && filled[c - '1']) {
                            return false;
                        } else if (c != '.') {
                            filled[c - '1'] = true;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkLine(char[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] filled = new boolean[9];
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.' && filled[c - '1']) {
                    return false;
                } else if (c != '.') {
                    filled[c - '1'] = true;
                }
            }
        }
        return true;
    }

    private boolean checkColumn(char[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] filled = new boolean[9];
            for (int j = 0; j < 9; j++) {
                char c = board[j][i];
                if (c != '.' && filled[c - '1']) {
                    return false;
                } else if (c != '.') {
                    filled[c - '1'] = true;
                }
            }
        }
        return true;
    }
}
