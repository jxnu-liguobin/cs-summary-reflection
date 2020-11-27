/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 归并 最坏时间复杂度O(NlogN) 分治
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class MergeSort extends Constant<Integer> {

    public static void main(String[] args) throws Exception {
        new MergeSort().sort(Constant.array, Constant.len);
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        Integer[] t = new Integer[len];
        mSort(array, t, 0, len - 1);
        super.printResult(array);
    }

    private void mSort(Integer[] arr, Integer[] temp, int left, int right) {
        int center;
        if (left < right) {
            center = (left + right) / 2;
            // 对前半部分进行排序
            mSort(arr, temp, left, center);
            // 对后半部分进行排序
            mSort(arr, temp, center + 1, right);
            // 合并
            // leftPos左半边的开始
            // rightPos右半边的开始
            merge(arr, temp, left, center + 1, right);
        }
    }

    private void merge(
            Integer[] arr, Integer[] tempArray, int leftPos, int rightPos, int rightEnd) {
        int i, leftEnd, numElements, tempPos;
        leftEnd = rightPos - 1;
        tempPos = leftPos;
        numElements = rightEnd - leftPos + 1;
        // 主循环
        while (leftPos <= leftEnd && rightPos <= rightEnd)
            if (arr[leftPos] <= arr[rightPos]) tempArray[tempPos++] = arr[leftPos++];
            else tempArray[tempPos++] = arr[rightPos++];
        // 复制第一个数组的剩余数据
        while (leftPos <= leftEnd) tempArray[tempPos++] = arr[leftPos++];
        // 复制第二个数组的剩余数据
        while (rightPos <= rightEnd) tempArray[tempPos++] = arr[rightPos++];
        // 把临时数组拷贝回来
        for (i = 0; i < numElements; i++, rightEnd--) {
            arr[rightEnd] = tempArray[rightEnd];
        }
    }
}
