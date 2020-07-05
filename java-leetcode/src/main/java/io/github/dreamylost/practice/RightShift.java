/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Arrays;

/**
 * Title: RightShift.java
 *
 * <p>Description: 数组循环右移K位
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-10
 * @version 1.0
 */
public class RightShift {

    /** @param args */
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        RightShift.rightShift3(array, array.length, 2);
        System.out.println(Arrays.toString(array));
    }

    /**
     * @description O(N*K)
     * @param array
     * @param size
     * @param k
     */
    public static void rightShift(int[] array, int size, int k) {
        while (k-- > 0) {
            int t = array[size - 1];
            for (int i = size - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = t;
        }
    }

    /**
     * @description O(N^2)
     * @param array
     * @param size
     * @param k
     */
    public static void rightShift2(int[] array, int size, int k) {
        k %= size;
        while (k-- > 0) {
            int t = array[size - 1];
            for (int i = size - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = t;
        }
    }

    public static void reverseArray(int[] array, int b, int e) {
        for (; b < e; b++, e--) {
            int temp = array[e];
            array[e] = array[b];
            array[b] = temp;
        }
    }

    /**
     * @description O(N)
     * @param array
     * @param size
     * @param k
     */
    public static void rightShift3(int[] array, int size, int k) {
        k %= size;
        reverseArray(array, 0, size - k - 1);
        reverseArray(array, size - k, size - 1);
        reverseArray(array, 0, size - 1);
    }
}
