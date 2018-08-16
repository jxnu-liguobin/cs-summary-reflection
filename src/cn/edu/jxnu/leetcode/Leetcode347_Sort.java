package cn.edu.jxnu.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 梦境迷离
 * @description 桶排序
 * 
 *  			出现频率最多的 k 个数
 * 
 *              Given [1,1,1,2,2,3] and k = 2, return [1,2].
 * @time 2018年4月8日
 */
public class Leetcode347_Sort {

	@SuppressWarnings("unchecked")
	public List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> ret = new ArrayList<>();
		Map<Integer, Integer> frequencyMap = new HashMap<>();
		for (int num : nums) {
			// getOrDefault,没有找到则返回一个默认值0
			frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
		}
		List<Integer>[] bucket = new List[nums.length + 1];
		for (int key : frequencyMap.keySet()) {
			// 桶的下标代表桶中数出现的频率
			int frequency = frequencyMap.get(key);
			// 设置若干个桶
			if (bucket[frequency] == null) {
				bucket[frequency] = new ArrayList<>();
			}
			// 每个桶存储出现频率相同的数
			bucket[frequency].add(key);
		}
		// 从后向前遍历桶，最先得到的 k个数就是出现频率最多的的 k 个数。
		for (int i = bucket.length - 1; i >= 0 && ret.size() < k; i--) {
			if (bucket[i] != null) {
				ret.addAll(bucket[i]);
			}
		}
		return ret;
	}
}
