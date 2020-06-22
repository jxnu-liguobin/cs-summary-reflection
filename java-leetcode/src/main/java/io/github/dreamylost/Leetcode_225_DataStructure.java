/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈
 *
 * <p>225. Implement Stack using Queues (Easy)
 *
 * <p>在将一个元素 x 插入队列时，需要让除了 x 之外的所有元素出队列，再入队列。此时 x 在队首，第一个出队列，实现了后进先出顺序。
 *
 * @author 梦境迷离.
 * @time 2018年6月30日
 * @version v1.0
 */
public class Leetcode_225_DataStructure {}

class MyStack {

    private Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
        int cnt = queue.size();
        while (cnt-- > 1) {
            queue.add(queue.poll());
        }
    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
