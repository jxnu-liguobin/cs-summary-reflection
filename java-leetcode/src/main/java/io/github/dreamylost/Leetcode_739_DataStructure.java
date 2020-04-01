package io.github.dreamylost;

import java.util.Stack;

/**
 * 数组中元素与下一个比它大的元素之间的距离
 * 
 * 739. Daily Temperatures (Medium)
 * 
 * Input: [73, 74, 75, 71, 69, 72, 76, 73] Output: [1, 1, 4, 2, 1, 1, 0, 0]
 * 
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_739_DataStructure {

	/**
	 * * 在遍历数组时用 Stack 把数组中的数存起来，如果当前遍历的数比栈顶元素来的大， 说明栈顶元素的下一个比它大的数就是当前元素。
	 * 
	 */
	public int[] dailyTemperatures(int[] temperatures) {
		int n = temperatures.length;
		int[] dist = new int[n];
		Stack<Integer> indexs = new Stack<>();
		for (int curIndex = 0; curIndex < n; curIndex++) {
			indexs.add(curIndex);
			while (!indexs.isEmpty() && temperatures[curIndex] > temperatures[indexs.peek()]) {
				int preIndex = indexs.pop();
				dist[preIndex] = curIndex - preIndex;
			}
		}
		return dist;
	}
}
