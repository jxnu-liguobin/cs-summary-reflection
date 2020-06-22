/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.sort;

/**
 * 1、插入排序 插入排序由N-1趟排序组成。对于P=1趟到P=N-1趟，插入排序保证位置0~P-1上的元素是已排序状态。即第N趟排序后，前N个元素是有序的。 插入排序分析
 * 平均复杂度O(n^2),而且这个界是精确的，因为反序输入可以达到该界【大O表示上界】 N个互异的数组，平均逆序数是N(N-1)/4 【2，1，0（逆序=3）】
 * 任何通过交换相邻元素进行排序的任何算法平均需要Ω(N^2) 【欧米伽表示下界，每次只能减少一个逆序，因此需要Ω(N^2)次交换】
 * 总结：为了使排序以亚二次或者o(N^2)运行，需要对相距较远的元素进行交换，即每次交换不能止删除一个逆序
 *
 * @time 2018年3月24日14:35:34
 */
public class InsertionSort extends Constant {
    public static void main(String[] args) throws Exception {
        Constant.printResult(new InsertionSort().sort(Constant.array, Constant.len));
    }

    @Override
    public Object[] sort(Object[] array, int len) {
        int j, p;
        int temp = 0;
        for (p = 1; p < len; p++) { // 外层循环固定是len-1趟
            temp = (int) array[p];
            for (j = p; j > 0 && temp < (int) array[j - 1]; j--) {
                array[j] = array[j - 1]; // j后移，j--
            }
            array[j] = temp; // 找到位置，插入
        }
        return array;
    }
}
