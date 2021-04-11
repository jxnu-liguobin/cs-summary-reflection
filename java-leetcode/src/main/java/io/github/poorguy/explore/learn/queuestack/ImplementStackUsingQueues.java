/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayDeque;
import java.util.Deque;

class ImplementStackUsingQueues {

    private Deque<Integer> a = new ArrayDeque<>();
    private Deque<Integer> b = new ArrayDeque<>();

    /** Initialize your data structure here. */
    public ImplementStackUsingQueues() {}

    /** Push element x onto stack. */
    public void push(int x) {
        if (a.isEmpty()) {
            a.offer(x);
            while (!b.isEmpty()) {
                a.offer(b.poll());
            }
        } else {
            b.offer(x);
            while (!a.isEmpty()) {
                b.offer(a.poll());
            }
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if (!a.isEmpty()) {
            return a.poll();
        } else {
            return b.poll();
        }
    }

    /** Get the top element. */
    public int top() {
        if (!a.isEmpty()) {
            return a.peek();
        } else {
            return b.peek();
        }
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return a.isEmpty() && b.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such: MyStack obj = new MyStack();
 * obj.push(x); int param_2 = obj.pop(); int param_3 = obj.top(); boolean param_4 = obj.empty();
 */
