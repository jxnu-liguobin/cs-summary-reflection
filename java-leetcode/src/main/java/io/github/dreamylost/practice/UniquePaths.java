package io.github.dreamylost.practice;

/**
 * 有一个机器人的位于一个 m × n 个网格左上角。 机器人每一时刻只能向下或者向右移动一步。机器人试图达到网格的右下角。 问有多少条不同的路径？
 * 
 * @author 梦境迷离
 * @time 2018年7月19日
 * @version v1.0
 */
public class UniquePaths {
	public int uniquePaths(int m, int n) {
		if (m == 0 || n == 0) {
			return 1;
		}

		int[][] sum = new int[m][n];
		for (int i = 0; i < m; i++) {
			sum[i][0] = 1;
		}
		for (int i = 0; i < n; i++) {
			sum[0][i] = 1;
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				sum[i][j] = sum[i - 1][j] + sum[i][j - 1];
			}
		}
		return sum[m - 1][n - 1];
	}

	public int uniquePaths2(int m, int n) {
		int[][] f = new int[m][n];
		int i, j;
		for (i = 0; i < m; ++i) {
			for (j = 0; j < n; ++j) {
				if (i == 0 || j == 0) {
					f[i][j] = 1;
				} else {
					f[i][j] = f[i - 1][j] + f[i][j - 1];
				}
			}
		}

		return f[m - 1][n - 1];
	}
}
