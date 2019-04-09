package cn.edu.jxnu.other;

import java.util.Scanner;

/*~  *  Created by lemon-clown on 2017/8/25  */

/** * 3*3 的智能机手势密码方案数 */
public class GesturePassword {
	private static int NUMBERS = 9;
	private static final int[][] G = new int[NUMBERS + 1][NUMBERS + 1];
	private static boolean[] visited = new boolean[NUMBERS + 1];

	static {
		G[1][3] = G[3][1] = 2;
		G[1][7] = G[7][1] = 4;
		G[1][9] = G[9][1] = 5;
		G[2][8] = G[8][2] = 5;
		G[3][7] = G[7][3] = 5;
		G[3][9] = G[9][3] = 6;
		G[4][6] = G[6][4] = 5;
		G[7][9] = G[9][7] = 8;
	}

	/** * MIN_NUMBERS: 最少连接点数 * MAX_NUMBERS: 最多连接点数 */
	private static int MIN_NUMBERS;
	private static int MAX_NUMBERS;

	private static int DFS(int o, int cur) {
		if (cur > MAX_NUMBERS)
			return 0;
		int cnt = (cur >= MIN_NUMBERS ? 1 : 0);
		visited[o] = true;
		for (int i = 1; i <= NUMBERS; ++i) {
			int target = G[o][i];
			if (visited[target] && !visited[i])
				cnt += DFS(i, cur + 1);
		}
		visited[o] = false;
		return cnt;
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			MIN_NUMBERS = in.nextInt();
			MAX_NUMBERS = in.nextInt();
			if (MIN_NUMBERS == 0 && MAX_NUMBERS == 0)
				break;
			int ans = DFS(0, 0);
			System.out.println(ans);
		}
	}
}