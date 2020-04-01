package io.github.dreamylost;

import java.util.Stack;

/**
 * 最小值栈
 * 
 * 155. Min Stack (Easy)
 * 
 * 对于实现最小值队列问题，可以先将队列使用栈来实现，然后就将问题转换为最小值栈，这个问题出现在 编程之美：3.7。
 * 
 * @author 梦境迷离.
 * @time 2018年6月30日
 * @version v1.0
 */
public class Leetcode_155_DataStructure {

}

class MinStack {

	private Stack<Integer> dataStack;
	private Stack<Integer> minStack;
	private int min;

	public MinStack() {
		dataStack = new Stack<>();
		minStack = new Stack<>();
		min = Integer.MAX_VALUE;
	}

	public void push(int x) {
		dataStack.add(x);
		min = Math.min(min, x);
		minStack.add(min);
	}

	public void pop() {
		dataStack.pop();
		minStack.pop();
		min = minStack.isEmpty() ? Integer.MAX_VALUE : minStack.peek();
	}

	public int top() {
		return dataStack.peek();
	}

	public int getMin() {
		return minStack.peek();
	}
}