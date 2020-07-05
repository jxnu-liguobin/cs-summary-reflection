/* All Contributors (C) 2020 */
package io.github.dreamylost;
/**
 * Leetcode : 79. Word Search (Medium) For example, Given board = [ ['A','B','C','E'],
 * ['S','F','C','S'], ['A','D','E','E'] ] word = "ABCCED", -> returns true, word = "SEE", -> returns
 * true, word = "ABCB", -> returns false.
 *
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月13日
 */
public class Leetcode_79_Backtracking {

    private static int[][] shift = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static boolean[][] visited;
    private int m;
    private int n;

    public static void main(String[] args) {
        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABC";
        boolean res = new Leetcode_79_Backtracking().exist(board, word);
        System.out.println(res);
    }

    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) return true;
        if (board == null || board.length == 0 || board[0].length == 0) return false;
        m = board.length;
        n = board[0].length;
        visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }

    /**
     * DFS遍历 @time. 下午5:44:53
     *
     * @version V1.0
     * @param board
     * @param word
     * @param start
     * @param r
     * @param c
     * @return 布尔
     */
    private boolean dfs(char[][] board, String word, int start, int r, int c) {
        if (start == word.length()) {
            return true;
        }
        // 排除坐标越界，已访问过，则返回false
        if (r < 0
                || r >= m
                || c < 0
                || c >= n
                || board[r][c] != word.charAt(start)
                || visited[r][c]) {
            return false;
        }
        // 从false->标记访问true
        visited[r][c] = true;
        // 四个方向上的遍历
        for (int i = 0; i < shift.length; i++) {
            int nextR = r + shift[i][0];
            int nextC = c + shift[i][1];
            if (dfs(board, word, start + 1, nextR, nextC)) {
                return true;
            }
        }
        // 不成功标记恢复true->false
        visited[r][c] = false;
        return false;
    }
}
