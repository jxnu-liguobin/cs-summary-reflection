package cn.edu.jxnu.leetcode;

/**
 * 数独
 * 
 * 难
 * 
 * 37. Sudoku Solver (Hard)
 * 
 * 
 * 编写一个程序，通过已填充的空格来解决数独问题。
 * 
 * 一个数独的解法需遵循如下规则：
 * 
 * 数字 1-9 在每一行只能出现一次。 数字 1-9 在每一列只能出现一次。 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。 空白格用
 * '.' 表示。
 * 
 * @author 梦境迷离.
 * @time 2018年5月12日
 * @version v1.0
 */
public class Leetcode_37_Backtracking {

	private boolean[][] rowsUsed = new boolean[9][10];
	private boolean[][] colsUsed = new boolean[9][10];
	private boolean[][] cubesUsed = new boolean[9][10];
	private char[][] board;

	public void solveSudoku(char[][] board) {
		/** 初始化 */
		this.board = board;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == '.')
					continue;
				// 字符转化为数值
				int num = board[i][j] - '0';
				// 标记为已有数字
				rowsUsed[i][num] = true;
				colsUsed[j][num] = true;
				// 标记3*3为已有数字
				cubesUsed[cubeNum(i, j)][num] = true;
			}

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				backtracking(i, j);
	}

	private boolean backtracking(int row, int col) {
		while (row < 9 && board[row][col] != '.') {
			row = col == 8 ? row + 1 : row;
			col = col == 8 ? 0 : col + 1;
		}

		if (row == 9)
			return true;

		for (int num = 1; num <= 9; num++) {
			if (rowsUsed[row][num] || colsUsed[col][num] || cubesUsed[cubeNum(row, col)][num])
				continue;
			rowsUsed[row][num] = colsUsed[col][num] = cubesUsed[cubeNum(row, col)][num] = true;
			board[row][col] = (char) (num + '0');
			if (backtracking(row, col))
				return true;
			board[row][col] = '.';
			rowsUsed[row][num] = colsUsed[col][num] = cubesUsed[cubeNum(row, col)][num] = false;
		}
		return false;
	}

	/** 粗线内包含3个小正方形，一个小正方形内也不能有重复 */
	private int cubeNum(int i, int j) {
		int r = i / 3;
		int c = j / 3;
		return r * 3 + c;
	}
}
