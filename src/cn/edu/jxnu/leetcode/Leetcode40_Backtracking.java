package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * 含有相同元素的求组合求和
 * 
 * 组合值等于8
 * 
 * For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
 * @author 梦境迷离.
 * @time 2018年4月21日
 * @version v1.0
 */
public class Leetcode40_Backtracking {
	
	public static void main(String[] args) {
		int[] arr = { 10, 1, 2, 7, 6, 1, 5 };
		int target = 8;
		List<List<Integer>> ret = new Leetcode40_Backtracking().combinationSum2(arr, target);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[\n");
		ret.forEach(x -> {
			String result = x.stream().map(j -> j.toString()).collect(Collectors.joining(",", "[", "]"));
			stringBuilder.append(" " + result);
			stringBuilder.append("\n");

		});
		stringBuilder.append("]");
		System.out.println(stringBuilder.toString());

	}

	private List<List<Integer>> ret;

	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		ret = new ArrayList<>();
		Arrays.sort(candidates);
		doCombination(candidates, target, 0, new ArrayList<>(), new boolean[candidates.length]);
		return ret;
	}

	private void doCombination(int[] candidates, int target, int start, List<Integer> list, boolean[] visited) {
		if (target == 0) {
			ret.add(new ArrayList<>(list));
			return;
		}
		for (int i = start; i < candidates.length; i++) {
			if (i != 0 && candidates[i] == candidates[i - 1] && !visited[i - 1])
				continue;
			if (candidates[i] <= target) {
				list.add(candidates[i]);
				visited[i] = true;
				doCombination(candidates, target - candidates[i], i + 1, list, visited);
				visited[i] = false;
				list.remove(list.size() - 1);
			}
		}
	}
}
