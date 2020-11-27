/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 快速排序 ，分治，递归 对于数组个数<=20，快排不如插入排序 枢纽元选取，一般选第一个，其实是最坏的 安全的方法：随即数生成，随即选取 或者三数中值分割法
 * 减少大约5%时间【一般选取左中右三个数】
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class QuickSort extends Constant<Integer> {
    public static void main(String[] args) throws Exception {
        new QuickSort().sort(array, len);
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        quick(array, 0, len - 1);
        super.printResult(array);
    }

    private void quick(Integer[] list, int low, int high) {
        if (low < high) {
            int middle = QuickSort.quickSortBy3Integers(list, low, high); // 三数中值分割法
            // int middle = quickSort(list, low, high); // 将list数组进行一分为二
            quick(list, low, middle - 1); // 对低字表进行递归排序
            quick(list, middle + 1, high); // 对高字表进行递归排序
            // 快速选择排序，这里需要if判断一下，少一次递归
        }
    }

    // 一般方法
    public int quickSort(Integer[] list, int low, int high) {
        int tmp = list[low]; // 数组的第一个作为中轴
        while (low < high) {
            while (low < high && list[high] >= tmp) {
                high--;
            }
            list[low] = list[high]; // 比中轴小的记录移到低端
            while (low < high && list[low] <= tmp) {
                low++;
            }
            list[high] = list[low]; // 比中轴大的记录移到高端
        }
        list[low] = tmp; // 中轴记录到尾
        return low;
    }

    // 使用三数中值
    private static int quickSortBy3Integers(Integer[] array, int low, int high) {
        // 三数取中
        int mid = low + (high - low) / 2;
        if (array[mid] > array[high]) {
            swap(array[mid], array[high]);
        }
        if (array[low] > array[high]) {
            swap(array[low], array[high]);
        }
        if (array[mid] > array[low]) {
            swap(array[mid], array[low]);
        }
        int key = array[low];

        while (low < high) {
            while (array[high] >= key && high > low) {
                high--;
            }
            array[low] = array[high];
            while (array[low] <= key && high > low) {
                low++;
            }
            array[high] = array[low];
        }
        array[high] = key;
        return high;
    }

    private static void swap(Object a, Object b) {
        Object temp = a;
        a = b;
        b = temp;
    }
}
