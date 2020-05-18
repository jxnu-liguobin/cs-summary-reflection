package io.github.dreamylost.practice;

/**
 * @description HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。
 *     今天测试组开完会后,他又发话了:在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
 *     但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2}, 连续子向量的最大和为8(从第0个开始,到第3个为止)。
 *     你会不会被他忽悠住？(子向量的长度至少是1)
 * @author Mr.Li
 */
public class FindGreatestSumOfSubArray {
    /**
     * @description 最优解
     * @param array
     * @return
     */
    public int findGreatestSumOfSubArray(int[] array) {
        int len = array.length;
        int start = array[len - 1];
        int all = array[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            if (start < 0) {
                start = 0;
            }
            start += array[i];
            if (start > all) {
                all = start;
            }
        }
        return all;
    }

    /**
     * @description 动态规划
     * @param array
     * @return
     */
    public int FindGreatestSumOfSubArray2(int[] array) {
        int len = array.length;
        int[] start = new int[len];
        int[] all = new int[len];
        start[len - 1] = array[len - 1];
        all[len - 1] = array[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            start[i] = Math.max(array[i], array[i] + start[i + 1]);
            all[i] = Math.max(start[i], all[i + 1]);
        }
        return all[0];
    }
}
