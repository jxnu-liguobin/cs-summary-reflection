/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.sort;

/**
 * @author 梦境迷离
 * @description 选择排序
 * @time 2018年4月8日
 */
public class SelectSort extends Constant {

    public static void main(String[] args) throws Exception {
        Constant.printResult(new SelectSort().sort(Constant.array, Constant.len));
    }

    @Override
    public Object[] sort(Object[] array, int len) {
        select(array, len);
        return array;
    }

    /**
     * @author 梦境迷离
     * @description 直接选择 首先我们选择排序的交换操作介于 0 和 (n - 1） 次之间。选择排序的比较操作为 n (n - 1） / 2 次之间。选择排序的赋值操作介于 0
     *     和 3 (n - 1） 次之间。 比较次数O(n^2），比较次数与关键字的初始状态无关，总的比较次数N=(n-1）+(n-2）+...+1=n*(n-1）
     *     /2。交换次数O(n），最好情况是，已经有序，交换0次；最坏情况交换n-1次，逆序交换n/2次。 交换次数比冒泡排序少多了， 由于交换所需CPU时间比比较所需的CPU时间多，
     *     n值较小时，选择排序比冒泡排序快。 稳定性： 选择排序是给每个位置选择当前元素最小的，比如给第一个位置选择最小的，在剩余元素里面给第二个元素选择第二小的，依次类推，
     *     直到第n-1个元素，第n个元素不用选择了，因为只剩下它一个最大的元素了。那么，在一趟选择，如果一个元素比当前元素小，
     *     而该小的元素又出现在一个和当前元素相等的元素后面，那么交换后稳定性就被破坏了。 比较拗口，举个例子，序列5 8 5 2
     *     9，我们知道第一遍选择第1个元素5会和2交换，那么原序列中两个5的相对前后顺序就被破坏了， 所以选择排序是一个不稳定的排序算法。
     * @time 2018年4月8日
     */
    public void select(Object a[], int length) {
        if (a == null || length <= 0) {
            return;
        }
        int minary; // 定义一个最小坐标
        int temp = 0; // 定义一个临时变量
        for (int i = 0; i < length - 1; i++) {
            minary = i; // 将最小下标附一个初始值
            for (int j = i + 1; j < length; j++) { // 遍历无序区 的元素
                if ((int) a[j] < (int) a[minary]) {
                    minary = j;
                }
            }
            if (minary != i) {
                temp = (int) a[i];
                a[i] = a[minary];
                a[minary] = temp;
            }
        }
    }
}
