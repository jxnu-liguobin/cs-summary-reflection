/* All Contributors (C) 2020 */
package io.github.dreamylost.tooffer;

/**
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。
 * 即输出P%1000000007
 */
public class T35 {

    int cnt;

    public int InversePairs(int[] array) {
        cnt = 0;
        if (array != null) {
            mergeSortUp2Down(array, 0, array.length - 1);
        }
        return cnt;
    }

    /**
     * 链接：https://www.nowcoder.com/questionTerminal/96bd6684e04a44eb80e6a68efc0ec6c5 来源：牛客网
     *
     * <p>归并排序的改进，把数据分成前后两个数组(递归分到每个数组仅有一个数据项)， 合并数组，合并时，出现前面的数组值array[i]大于后面数组值array[j]时；则前面
     * 数组array[i]~array[mid]都是大于array[j]的，count += mid+1 - i 参考剑指Offer，但是感觉剑指Offer归并过程少了一步拷贝过程。
     * 还有就是测试用例输出结果比较大，对每次返回的count mod(1000000007)求余
     */
    public void merge(int[] a, int start, int mid, int end) {
        int[] tmp = new int[end - start + 1];

        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (a[i] <= a[j]) tmp[k++] = a[i++];
            else {
                tmp[k++] = a[j++];
                cnt += mid - i + 1; // core code, calculate InversePairs............
                cnt %= 1000000007; // 不然溢出
            }
        }

        while (i <= mid) tmp[k++] = a[i++];
        while (j <= end) tmp[k++] = a[j++];
        for (k = 0; k < tmp.length; k++) a[start + k] = tmp[k];
    }

    public void mergeSortUp2Down(int[] a, int start, int end) {
        if (start >= end) return;
        int mid = (start + end) >> 1;
        mergeSortUp2Down(a, start, mid);
        mergeSortUp2Down(a, mid + 1, end);
        merge(a, start, mid, end);
    }
}
