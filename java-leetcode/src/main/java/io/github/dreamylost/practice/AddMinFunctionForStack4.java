package io.github.dreamylost.practice;

/**
 * @description 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 * @note:使用ArrayList
 * @author Mr.Li
 */
public class AddMinFunctionForStack4 {

    private java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>();

    public void push(int node) {
        list.add(0, node);
    }

    public void pop() {
        list.get(0);
        list.remove(0);
    }

    public int top() {
        return list.get(0).intValue();
    }

    public int min() {
        int temp = top();
        for (int i = 1; i < list.size(); i++) {
            if (temp > list.get(i).intValue()) {
                temp = list.get(i).intValue();
            }
        }
        return temp;
    }
}
