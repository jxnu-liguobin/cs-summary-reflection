package io.github.dreamylost.practice;

/**
 * @description 输入一个递增排序的数组和一个数字S，在数组中查找两个数，是的他们的和正好是S， 如果有多对数字的和等于S，输出两个数的乘积最小的。 输出描述:
 *     对应每个测试案例，输出两个数，小的先输出。
 * @author Mr.Li
 */
public class FindNumbers {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        java.util.ArrayList<Integer> list = new FindNumbers().FindNumbersWithSum(array, 15);
        for (Integer integer : list) {
            System.out.print(integer);
        }
    }

    /**
     * @description 使用夾逼 數組必須是已經排序的
     * @param array
     * @param sum
     * @return
     * @time 20点25分
     */
    public java.util.ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {

        java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
        if (array.length == 0 || array == null) return list;
        int len = array.length;
        int end = len - 1;
        int begin = 0;
        int muti = 0;
        while (begin < end) {
            int currSum = array[begin] + array[end];
            if (sum == currSum) {
                // 满足条件的第一对
                if (list.size() == 0) {
                    // 保存乘积
                    muti = array[begin] * array[end];
                    list.add(array[begin]);
                    list.add(array[end]);
                } else {
                    // 满足条件的第2队 判断是否大于前面一对的乘积
                    if (array[begin] * array[end] < muti) {
                        muti = array[begin] * array[end];
                        // 清空链表，重新填入
                        list.clear();
                        list.add(array[begin]);
                        list.add(array[end]);
                    }
                }
                begin++;
                end--;
            } else {
                if (currSum < sum) {
                    begin++;
                } else {
                    end--;
                }
            }
        }
        return list;
    }
}
