/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
 *     对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”, 要求输出循环左移3位后的结果，即“
 *     XYZdefabc”。是不是很简单？OK，搞定它！
 * @author Mr.Li
 */
public class LeftRotate {

    public static void main(String[] args) {
        String res = new LeftRotate().LeftRotateString("abcdefg", 2);
        System.out.println(res);
    }

    /**
     * @description 注意当 n>k的 求余
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString(String str, int n) {
        if (str == null || "".equals(str)) return "";
        char arr[] = str.toCharArray();
        rightShift(arr, arr.length, n);
        return String.valueOf(arr);
    }

    /**
     * @description 移位
     * @param array
     * @param b
     * @param e
     */
    private static void reverseArray(char[] array, int b, int e) {
        for (; b < e; b++, e--) {
            char temp = array[e];
            array[e] = array[b];
            array[b] = temp;
        }
    }

    /**
     * @description 时间复杂度O(N) 交互0~k-1 交换k~size-1 交换0~size-1 整体效果=循环移位 【左右移动同理，只需要改变下标】
     * @param array
     * @param size
     * @param k
     */
    private static void rightShift(char[] array, int size, int k) {
        k %= size;
        reverseArray(array, 0, k - 1);
        reverseArray(array, k, size - 1);
        reverseArray(array, 0, size - 1);
    }
}
