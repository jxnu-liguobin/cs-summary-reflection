/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.Stack;

/**
 * 用栈实现队列
 *
 * <p>232. Implement Queue using Stacks (Easy)
 *
 * @author 梦境迷离.
 * @time 2018年6月30日
 * @version v1.0
 */
public class Leetcode_232_DataStructure {}

class MyQueue {

    private Stack<Integer> in = new Stack<>(); // 入队
    private Stack<Integer> out = new Stack<>(); // 出队

    // 进队
    public void push(int x) {
        in.push(x);
    }

    // 出队
    public int pop() {
        in2out();
        return out.pop();
    }

    // 获取队列首部但不删除元素
    public int peek() {
        in2out();
        return out.peek();
    }

    // 内部使用in----->out
    private void in2out() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                // 如果用于出队的栈中没有元素，就将用于入队的栈中的元素出队并放进出队栈中
                out.push(in.pop());
            }
        }
    }

    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
