/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @description 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 * @note:装饰Stack类
 * @author Mr.Li
 */
public class AddMinFunctionForStack {

    public static void main(String[] args) {
        AddMinFunctionForStack s = new AddMinFunctionForStack();
        s.push(0);
        s.push(8);
        s.push(2);
        s.push(2);
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(6);
        System.out.println(s.min());
    }

    /** @description 使用单个私有栈 */
    private Stack<Integer> stack = new Stack<>();

    public void push(int node) {
        stack.push(node);
    }

    public void pop() {
        if (stack.isEmpty())
            try {
                throw new EmptyStackException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        stack.pop();
    }

    public int top() {
        if (stack.isEmpty())
            try {
                throw new EmptyStackException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return stack.peek();
    }

    public int min() {
        if (stack.isEmpty())
            try {
                throw new EmptyStackException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        int min = stack.peek();
        int temp = 0;
        java.util.Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (min > temp) {
                min = temp;
            }
        }
        return min;
    }
}
