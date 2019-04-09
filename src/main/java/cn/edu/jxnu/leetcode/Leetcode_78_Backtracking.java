package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 子集
 * 
 * Leetcode : 78. Subsets (Medium)
 * 
 * 题目描述：找出集合[无重复元素]的所有子集，子集不能重复，[1, 2] 和 [2, 1] 这种子集算重复
 * 
 * @author 梦境迷离.
 * @time 2018年4月21日
 * @version v1.0
 */
public class Leetcode_78_Backtracking {

	public static void main(String[] args) {
		int[] arr = {1,2,3,4};
		List<List<Integer>> ret = new Leetcode_78_Backtracking().subsets(arr);
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

	private List<List<Integer>> ret;
	private List<Integer> subsetList;

	public List<List<Integer>> subsets(int[] nums) {
		ret = new ArrayList<>();
		subsetList = new ArrayList<>();
		// 不同的子集大小
		for (int i = 0; i <= nums.length; i++) {
			backtracking(0, i, nums);
		}
		return ret;
	}

	private void backtracking(int startIdx, int size, int[] nums) {
		if (subsetList.size() == size) {
			ret.add(new ArrayList<Integer>(subsetList));
			return;
		}

		for (int i = startIdx; i < nums.length; i++) {
			subsetList.add(nums[i]);
			backtracking(i + 1, size, nums);
			subsetList.remove(subsetList.size() - 1);
		}
	}

}
