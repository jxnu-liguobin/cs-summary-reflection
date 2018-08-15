package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * 从 1-9 数字中选出 k 个数，使得它们的和为 n。
 * 
 * Input: k = 3, n = 9
 * 
 * 1~9的数字,k[3]位数组合和为n[9] Output:
 * 
 * [[1,2,6], [1,3,5], [2,3,4]]
 * 
 * @author 梦境迷离.
 * @time 2018年4月21日
 * @version v1.0
 */
public class Leetcode216_Backtracking {

	public static void main(String[] args) {
		int k = 3, n = 9;
		List<List<Integer>> ret = new Leetcode216_Backtracking().combinationSum3(k, n);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[\n");
		ret.forEach(x -> {
			String result = x.stream().map(j -> j.toString()).collect(Collectors.joining(",", "[", "],"));
			stringBuilder.append(" " + result);
			stringBuilder.append("\n");

		});
		stringBuilder.append("]");
		System.out.println(stringBuilder.toString());

	}

	public List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> ret = new ArrayList<>();
		List<Integer> path = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			path.add(i);
			backtracking(k - 1, n - i, path, i, ret);
			path.remove(0);
		}
		return ret;
	}

	/**
	 * k个数，和为n的回溯
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月21日
	 * @version v1.0
	 * @param k
	 * @param n
	 * @param path
	 *            临时结果集
	 * @param start
	 *            开启标记位
	 * @param ret
	 *            结果集
	 */
	private void backtracking(int k, int n, List<Integer> path, int start, List<List<Integer>> ret) {
		if (k == 0 && n == 0) {
			ret.add(new ArrayList<>(path));
			return;
		}
		if (k == 0 || n == 0)
			return;
		// 只能访问下一个元素，防止遍历的结果重复
		for (int i = start + 1; i <= 9; i++) {
			path.add(i);
			backtracking(k - 1, n - i, path, i, ret);
			path.remove(path.size() - 1);
		}
	}
}
