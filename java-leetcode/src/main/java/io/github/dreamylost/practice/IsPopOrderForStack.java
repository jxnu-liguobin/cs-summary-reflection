/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 输入两个整数序列，第一个序列表示栈的压入顺序， 请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
 *     例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列， 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
 * @author Mr.Li
 */
public class IsPopOrderForStack {
    public boolean isPopOrder(int[] pushA, int[] popA) {
        if (pushA.length == 0 || popA.length == 0) {
            return false;
        }
        java.util.Stack<Integer> s = new java.util.Stack<Integer>();
        // 用于标识弹出序列的位置
        int popIndex = 0;
        for (int i = 0; i < pushA.length; i++) {
            s.push(pushA[i]);
            // 如果栈不为空，且栈顶元素等于弹出序列
            while (!s.empty() && s.peek() == popA[popIndex]) {
                // 出栈
                s.pop();
                // 弹出序列向后一位
                popIndex++;
            }
        }
        return s.empty();
    }
}
