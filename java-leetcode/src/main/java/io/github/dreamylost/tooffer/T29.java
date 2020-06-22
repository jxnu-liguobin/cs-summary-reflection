/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

import java.util.ArrayList;

/** 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。 */
public class T29 {

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> integers = new ArrayList<>();
        if (k > input.length) {
            return integers;
        }
        for (int i = 1; i < input.length; i++) {
            for (int j = input.length - 1; j > 0; j--) {
                if (input[j] < input[j - 1]) {
                    int temp = input[j];
                    input[j] = input[j - 1];
                    input[j - 1] = temp;
                }
            }
        }

        for (int i = 0; i < k; i++) {
            integers.add(input[i]);
        }
        return integers;
    }
}
