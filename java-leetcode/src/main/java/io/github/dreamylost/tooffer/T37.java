/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/** 统计一个数字在排序数组中出现的次数。 */
public class T37 {

    public int GetNumberOfK(int[] array, int k) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        return count;
    }
}
