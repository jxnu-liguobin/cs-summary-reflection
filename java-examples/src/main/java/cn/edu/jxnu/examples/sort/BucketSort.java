/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 桶排序
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class BucketSort extends Constant<Integer> {

    public static void main(String[] args) throws Exception {
        new BucketSort().sort(array, len);
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        bucketSort(array);
    }

    /**
     * 1）待排序列的值处于一个可枚举的范围内 2）待排序列所在可枚举范围不应太大，不然开销会很大。
     *
     * <p>桶排序的基本思想是： 把数组 arr 划分为n个大小相同子区间（桶），每个子区间各自排序，最后合并 。
     * 计数排序是桶排序的一种特殊情况，可以把计数排序当成每个桶里只有一个元素的情况。 1.找出待排序数组中的最大值max、最小值min
     *
     * <p>2.我们使用 动态数组ArrayList 作为桶，桶里放的元素也用 ArrayList 存储。桶的数量为(max-min)/arr.length+1
     *
     * <p>3.遍历数组 arr，计算每个元素 arr[i] 放的桶
     *
     * <p>4.每个桶各自排序
     *
     * <p>5.遍历桶数组，把排序好的元素放进输出数组
     */
    private void bucketSort(Integer[] arr) throws Exception {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (Integer item : arr) {
            max = Math.max(max, item);
            min = Math.min(min, item);
        }
        // 桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<>());
        }
        // 将每个元素放入桶
        for (Integer value : arr) {
            int num = (value - (Integer) min) / (arr.length);
            bucketArr.get(num).add(value);
        }
        // 对每个桶进行排序
        for (ArrayList<Integer> integers : bucketArr) {
            Collections.sort(integers);
        }
        int i = 0;
        for (ArrayList<Integer> arrayList : bucketArr) {
            for (Integer integer : arrayList) {
                arr[i++] = integer;
                if (i == arr.length - 1) break;
            }
        }
        System.out.println("使用toString()方法：" + bucketArr.toString());
        super.printResult(arr);
    }
}
