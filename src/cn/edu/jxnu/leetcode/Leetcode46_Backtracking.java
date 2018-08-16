package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 排列
	Leetcode : 46. Permutations (Medium)
	[1,2,3] have the following permutations:
	[
	  [1,2,3],
	  [1,3,2],
	  [2,1,3],
	  [2,3,1],
	  [3,1,2],
	  [3,2,1]
	]
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月13日
 */
public class Leetcode46_Backtracking {
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> ret = new ArrayList<>();
		List<Integer> permuteList = new ArrayList<>();
		//标记
		boolean[] visited = new boolean[nums.length];
		backtracking(permuteList, visited, nums, ret);
		return ret;
	}

	/**
	 * 
	 * 回朔
	 * @time.	下午6:16:40
	 * @version V1.0
	 * @param permuteList
	 * @param visited
	 * @param nums
	 * @param ret
	 */
	private void backtracking(List<Integer> permuteList, boolean[] visited, int[] nums, List<List<Integer>> ret) {
		if (permuteList.size() == nums.length) {
			ret.add(new ArrayList<Integer>(permuteList));
			return;
		}

		for (int i = 0; i < visited.length; i++) {
			// 已经访问过，进行下一次
			if (visited[i])
				continue;
			// 标记为已访问过
			visited[i] = true;
			permuteList.add(nums[i]);
			backtracking(permuteList, visited, nums, ret);
			permuteList.remove(permuteList.size() - 1);// 复位
			visited[i] = false;
		}
	}
}
