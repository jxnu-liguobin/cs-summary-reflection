/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Stack;

/**
 * @description 使用独立的辅助栈
 * @note:装饰Stack类
 * @author Mr.Li
 */
public class AddMinFunctionForStack2 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
        if (stack2.size() == 0) {
            stack2.push(node);
        } else {
            // 存放最小值的辅助栈
            int minVal;
            if (stack2.peek() < node) {
                minVal = stack2.peek();
            } else {
                minVal = node;
            }
            stack2.push(minVal);
        }
    }

    public void pop() {
        stack1.pop();
        stack2.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }
}
