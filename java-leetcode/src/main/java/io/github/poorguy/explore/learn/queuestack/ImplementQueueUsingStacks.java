/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayDeque;
import java.util.Deque;

class ImplementQueueUsingStacks {

    private Deque<Integer> in = new ArrayDeque<>();
    private Deque<Integer> out = new ArrayDeque<>();

    /** Initialize your data structure here. */
    public ImplementQueueUsingStacks() {}

    /** Push element x to the back of queue. */
    public void push(int x) {
        in.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

        if (!out.isEmpty()) {
            return out.pop();
        }
        return -1;
    }

    /** Get the front element. */
    public int peek() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

        if (!out.isEmpty()) {
            return out.peekFirst();
        }
        return -1;
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return out.isEmpty() && in.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such: MyQueue obj = new MyQueue();
 * obj.push(x); int param_2 = obj.pop(); int param_3 = obj.peek(); boolean param_4 = obj.empty();
 */
