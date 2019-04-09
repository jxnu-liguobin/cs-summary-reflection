package cn.edu.jxnu.leetcode.dp;

/**
 * 复制粘贴字符
 * 
 * 650. 2 Keys Keyboard (Medium)
 * 
 * 题目描述：最开始只有一个字符 A，问需要多少次操作能够得到 n 个字符 A，每次操作可以复制当前所有的字符，或者粘贴。
	
	Input: 3
	Output: 3
	Explanation:
	Intitally, we have one character 'A'.
	In step 1, we use Copy All operation.
	In step 2, we use Paste operation to get 'AA'.
	In step 3, we use Paste operation to get 'AAA'.

	经过x次得到含有n个A的字符串
 * 
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_650_Dp {

	public static void main(String[] args) {
		int n = 2;
		int ret = Leetcode_650_Dp.minSteps2(n);
		System.out.println(ret);
	}

	public static int minSteps(int n) {
		if (n == 1)
			return 0;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0)
				return i + minSteps(n / i);
		}
		return n;
	}

	public static int minSteps2(int n) {
		int[] dp = new int[n + 1];
		int h = (int) Math.sqrt(n);
		for (int i = 2; i <= n; i++) {
			dp[i] = i;
			for (int j = 2; j <= h; j++) {
				if (i % j == 0) {
					dp[i] = dp[j] + dp[i / j];
					break;
				}
			}
		}
		return dp[n];
	}
}
