package io.github.dreamylost.tooffer;

import java.util.Iterator;
import java.util.Stack;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 */
public class T20 {

    Stack<Integer> stack = new Stack<>();

    public void push(int node) {
        stack.push(node);
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        int min = top();
        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            int temp = iterator.next();
            if (min > temp) {
                min = temp;
            }
        }
        return min;
    }

}
