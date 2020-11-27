/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 基数排序 按照低位优先
 *
 * @author 梦境迷离
 * @version 2020年11月27日
 */
public class RadixSort extends Constant<Integer> {
    public static void main(String[] args) throws Exception {
        new RadixSort().sort(Constant.array, Constant.len);
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        int max = array[0];
        // 计算数组中的最大值
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        // 基数1，10，100，1000，...
        int bit = 1;
        while (max / bit > 0) {
            radix(array, bit);
            bit *= 10;
        }
        super.printResult(array);
    }

    private void radix(Integer[] array, int bit) {
        // 临时数组存储所有元素
        Integer[] temp = new Integer[array.length];
        // 0~9十个桶，每个桶存放的是计算出来的该位上数的个数
        int[] bucket = new int[10];
        for (Integer integer : array) {
            bucket[(integer / bit) % 10]++;
        }
        // 再次遍历这个桶数组，将桶里数的个数变为 前面桶的个数加上自己桶里的个数 (bucket[i] += bucket[i-1]) 这一步完成之后
        // 我们就可以从桶里知道对应数排序后的位置
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] += bucket[i - 1];
        }
        // 从后面开始排序
        for (int i = array.length - 1; i >= 0; i--) {
            // 根据该数对应位上的数字找到对应的桶从而得到桶里的数字
            // 计数=索引值+1
            int index = (array[i] / bit) % 10;
            temp[bucket[index] - 1] = array[i];
            bucket[index]--;
        }
        // 每次根据一位进行排序，都需要拷贝回原数组
        System.arraycopy(temp, 0, array, 0, temp.length);
    }
}
