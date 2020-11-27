/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 计数排序
 *
 * <p>计数排序 计数排序适用数据范围 计数排序需要占用大量空间，它仅适用于数据比较集中的情况。比如 [0~100]，[10000~19999] 这样的数据。 最佳情况：T(n) = O(n+k)
 * 最差情况：T(n) = O(n+k) 平均情况：T(n) = O(n+k)
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class CountSort extends Constant<Integer> {

    private static long time = 0L;

    public static void main(String[] args) throws Exception {
        new CountSort().sort(Constant.array2, Constant.len);
        System.out.println("耗费时间：" + time); // array1：18606,array2：18927 几乎相同
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        countSort(array);
    }

    /**
     * 计数排序
     *
     * <p>1.得到最大值与最小值，并计算最大值与最小值之差，制造桶，此时桶的下标i并非真正的元素，而是元素与min之差 2.统计每个元素出现的次数，并记录在桶中
     * 3.对桶中的元素进行取出，注意有重复的元素，而此时元素是i与min之和
     */
    private void countSort(Integer[] arr) throws Exception {
        long t = System.nanoTime();

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        // 找出数组中的最大最小值
        for (Integer value : arr) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }
        // 比max大1
        int[] help = new int[max - min + 1];
        // 找出每个数字出现的次数
        for (Integer integer : arr) {
            int mapPos = integer - min;
            help[mapPos]++;
        }
        int index = 0;
        for (int i = 0; i < help.length; i++) {
            // 因为可能有重复的数据，所以需要循环
            while (help[i]-- > 0) {
                arr[index++] = i + min;
            }
        }

        time = System.nanoTime() - t;
        super.printResult(arr);
    }
}
