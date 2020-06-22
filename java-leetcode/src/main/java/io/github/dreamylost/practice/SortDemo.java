/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。 输入一个数组,求出这个数组中的逆序对的总数P。
 *     并将P对1000000007取模的结果输出。 即输出P%1000000007 输入描述: 题目保证输入的数组中没有的相同的数字 数据范围： 对于%50的数据,size<=10^4
 *     对于%75的数据,size<=10^5 对于%100的数据,size<=2*10^5
 *     <p>示例1 输入 1,2,3,4,5,6,7,0 输出 7
 * @author Mr.Li
 */
public class SortDemo {

    public static void main(String[] args) {
        int[] array = {3, 2, 1, 1, 1};
        int re = new SortDemo().InversePairs2(array);
        System.out.println(re);
    }

    /**
     * @description 选择排序 测试有问题
     * @param array
     * @return
     */
    public int InversePairs(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    res++;
                    // 记录位置
                }
            }
            // 并交换
        }
        res = res % 1000000007;
        return res;
    }

    /** ******* 归并排序 *********************************************************** */
    int[] bak;

    int count = 0;

    public int InversePairs2(int[] array) {
        if (array == null) return 0;
        bak = new int[array.length];
        mergeSort(array, 0, array.length - 1);
        return count % 1000000007;
    }

    /**
     * @description 统计子数组逆序，排序子数组，合并统计逆序 过程：先把数组分割成子数组，先统计出子数组内部的逆序对的数目，然后再统计出两个相邻子数组之间的逆序对的数目。
     *     在统计逆序对的过程中，还需要对数组进行排序。
     * @param array
     * @param start
     * @param end
     */
    private void mergeSort(int[] array, int start, int end) {
        if (start >= end) return;
        int mid = start + (end - start) / 2;
        /*
         * 归并排序(从上往下)
         */
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    /*
     * 归并排序的改进，把数据分成前后两个数组(递归分到每个数组仅有一个数据项)，
     * 合并数组，合并时，出现前面的数组值array[i]大于后面数组值array[j]时；则前面
     * 数组array[i]~array[mid]都是大于array[j]的，count += mid+1 - i
     * 参考剑指Offer，但是感觉剑指Offer归并过程少了一步拷贝过程。 还有就是测试用例输出结果比较大，对每次返回的count
     * mod(1000000007)求余
     */
    /*
     * 将一个数组中的两个相邻有序区间合并成一个
     */
    private void merge(int[] array, int start, int mid, int end) {
        if (array[mid] <= array[mid + 1]) return;
        System.arraycopy(array, start, bak, start, end - start + 1);
        int i = start, j = mid + 1;
        for (int k = start; k <= end; k++) {
            if (i > mid) {
                array[k] = bak[j++];
            } else if (j > end) {
                array[k] = bak[i++];
            } else if (bak[j] < bak[i]) {
                array[k] = bak[j++];
                count += mid - i + 1;
                count %= 1000000007;
            } else {
                array[k] = bak[i++];
            }
        }
    }
}
