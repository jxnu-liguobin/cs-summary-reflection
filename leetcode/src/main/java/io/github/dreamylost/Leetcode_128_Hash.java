package io.github.dreamylost;

import java.util.HashMap;
import java.util.Map;

/**
 * 最长连续序列
 * 
 * 128. Longest Consecutive Sequence (Hard)
 * 
 * Given [100, 4, 200, 1, 3, 2], The longest consecutive elements sequence is
 * [1, 2, 3, 4]. Return its length: 4.
 * 
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_128_Hash {

	/**
	 * 我们将使用HashMap。关键是要跟踪序列长度，并将其存储在序列的边界点中。
	 * 
	 * 例如，对于序列{1，2，3，4，5}，map.get(1)和map.get(5)都应该返回5。
	 * 
	 * 无论何时将新元素n插入到映射中，都要做两件事：
	 *
	 * 1、看看映射中是否存在n-1和n+1，如果存在，则表示在n旁边有一个现有序列。
	 * 
	 * 左变量和右变量将是这两个序列的长度，而0表示没有序列，n稍后将是边界点。将(左+右+1)作为键n的关联值存储到映射中。
	 * 
	 * 2、使用左和右将序列的另一端分别定位到n的左和右，并将值替换为新长度。
	 */
	public int longestConsecutive2(int[] num) {
		int res = 0;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int n : num) {
			if (!map.containsKey(n)) {
				int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
				int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
				// 序列n的长度
				int sum = left + right + 1;
				map.put(n, sum);
				// 跟踪最大长度
				res = Math.max(res, sum);
				// 如果n没有邻居，则将长度扩展到序列的边界不会起任何作用
				map.put(n - left, sum);
				map.put(n + right, sum);
			} else {
				continue;
			}
		}
		return res;
	}

	public int longestConsecutive(int[] nums) {
		Map<Integer, Integer> countForNum = new HashMap<>();
		for (int num : nums) {
			countForNum.put(num, 1);
		}
		for (int num : nums) {
			forward(countForNum, num);
		}
		return maxCount(countForNum);
	}

	private int forward(Map<Integer, Integer> countForNum, int num) {
		if (!countForNum.containsKey(num)) {
			return 0;
		}
		int cnt = countForNum.get(num);
		if (cnt > 1) {
			return cnt;
		}
		cnt = forward(countForNum, num + 1) + 1;
		countForNum.put(num, cnt);
		return cnt;
	}

	/**
	 * 获取map最大值
	 */
	private int maxCount(Map<Integer, Integer> countForNum) {
		int max = 0;
		for (int num : countForNum.keySet()) {
			max = Math.max(max, countForNum.get(num));
		}
		return max;
	}
}
