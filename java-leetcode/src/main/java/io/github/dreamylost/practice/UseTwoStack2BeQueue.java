package io.github.dreamylost.practice;

import java.util.Stack;

/**
 * @description 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 * @author Mr.Li
 * 
 */
public class UseTwoStack2BeQueue {
	public static void main(String[] args) {
		UseTwoStack2BeQueue uBeQueue = new UseTwoStack2BeQueue();
		uBeQueue.push(5);
		uBeQueue.push(6);
		uBeQueue.push(7);
		uBeQueue.push(8);
		uBeQueue.push(9);
		System.out.println("第一个出队的是：" + uBeQueue.pop());
		System.out.println("第二个出队的是：" + uBeQueue.pop());
		System.out.println("第三个出队的是：" + uBeQueue.pop());
		System.out.println("第四个出队的是：" + uBeQueue.pop());
		System.out.println("第五个出队的是：" + uBeQueue.pop());
		System.out.println("空队抛异常操作：" + uBeQueue.pop());
	}

	Stack<Integer> stack1 = new Stack<Integer>();
	Stack<Integer> stack2 = new Stack<Integer>();

	/**
	 * [stack2 stack1] 即1进2出
	 * 
	 * @param node
	 */
	public void push(int node) {
		stack1.push(node);

	}

	public int pop() {
		if (stack1.empty() && stack2.empty()) {
			throw new RuntimeException("Queue is empty!");
		}
		if (stack2.empty()) {
			while (!stack1.empty()) {
				/* 把1的推出，并放入2 直到1空 */
				stack2.push(stack1.pop());
			}
		}
		return stack2.pop();

	}
}