package cn.edu.jxnu.practice;

import java.util.Arrays;
import java.util.Stack;

/**
 * @description 借用辅助栈存储min的大小，自定义了栈结构
 * @note:装饰Stack类
 */
public class AddMinFunctionForStack3 {
	private int size;
	private int min = Integer.MAX_VALUE;
	private Stack<Integer> minStack = new Stack<Integer>();
	private Integer[] elements = new Integer[10];

	public void push(int node) {
		ensureCapacity(size + 1);
		elements[size++] = node;
		if (node <= min) {
			minStack.push(node);
			min = minStack.peek();
		} else {
			minStack.push(min);
		}
	}

	private void ensureCapacity(int size) {
		int len = elements.length;
		if (size > len) {
			int newLen = (len * 3) / 2 + 1; // 每次扩容方式
			elements = Arrays.copyOf(elements, newLen);
		}
	}

	public void pop() {
		Integer top = top();
		if (top != null) {
			elements[size - 1] = (Integer) null;
		}
		size--;
		minStack.pop();
		min = minStack.peek();
	}

	@SuppressWarnings("null")
	public int top() {
		if (!empty()) {
			if (size - 1 >= 0)
				return elements[size - 1];
		}
		return (Integer) null;
	}

	public boolean empty() {
		return size == 0;
	}

	public int min() {
		return min;
	}
}