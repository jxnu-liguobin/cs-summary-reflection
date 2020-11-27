/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 冒泡排序
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class BubbleSort extends Constant<Integer> {

    private static long time = 0L;
    private static long time2 = 0L;

    public static void main(String[] args) throws Exception {
        new BubbleSort().sort(array2, len); // array:13474,array2:12832 相差明显
        new BubbleSort().sort2(array2, len); // array:1284,array2:1283
        System.out.println("没有优化：" + time);
        System.out.println("优化：" + time2);
    }

    /** 原始版 */
    @Override
    public void sort(Integer[] array, int len) throws Exception {
        long t = System.nanoTime();
        for (int i = 0; i < array.length - 1; i++) { // 外层循环控制排序趟数
            for (int j = 0; j < array.length - 1 - i; j++) { // 内层循环控制每一趟排序多少次
                if (array[j] > array[j + 1]) {
                    Integer temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        time = System.nanoTime() - t;
        super.printResult(array);
    }

    /** 优化版 */
    @Override
    public void sort2(Integer[] array, int len) throws Exception {
        long t = System.nanoTime();
        boolean flag;
        for (int i = 0; i < array.length; i++) {
            flag = true;
            // 思路是每次进下一趟冒泡的时候给flag设置true,如果被修改说明还有元素没有被排序，继续重复操作
            // 如果经过一趟下来，没有元素被交换【没有设置flag=false】,此时说明元素全部有序，直接退出
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    Integer temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        time2 = System.nanoTime() - t;
        super.printResult(array);
    }
}
