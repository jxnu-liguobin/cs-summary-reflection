/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayList;
import java.util.List;

class MinStack {
    private List<Integer> list = new ArrayList<>();
    private int index = -1;

    /** initialize your data structure here. */
    public MinStack() {}

    public void push(int val) {
        list.add(val);
        index++;
    }

    public void pop() {
        list.remove(index);
        index--;
    }

    public int top() {
        return list.get(index);
    }

    public int getMin() {
        int min = list.get(0);
        for (Integer num : list) {
            if (min > num) {
                min = num;
            }
        }
        return min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such: MinStack obj = new MinStack();
 * obj.push(val); obj.pop(); int param_3 = obj.top(); int param_4 = obj.getMin();
 */
