package io.github.dreamylost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 含有相同元素求子集
 * For example,
If nums = [1,2,2], a solution is:

[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
 * Leetcode : 90. Subsets II (Medium)
 * 
 * @author 梦境迷离.
 * @time 2018年4月22日
 * @version v1.0
 */
public class Leetcode_90_Backtracking {

	private List<List<Integer>> ret;
	private List<Integer> subsetList;
	private boolean[] visited;

	public List<List<Integer>> subsetsWithDup(int[] nums) {
		ret = new ArrayList<>();
		subsetList = new ArrayList<>();
		visited = new boolean[nums.length];
		Arrays.sort(nums);
		// 循环集合的个数，存在的子集大小在0~length之间
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
			if (i != 0 && nums[i] == nums[i - 1] && !visited[i - 1])
				continue;
			subsetList.add(nums[i]);
			visited[i] = true;
			backtracking(i + 1, size, nums);
			visited[i] = false;
			subsetList.remove(subsetList.size() - 1);
		}
	}

}
