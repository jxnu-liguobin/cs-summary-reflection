package io.github.dreamylost.practice;

import java.util.ArrayList;

/**
 * @description 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,
 *     他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数
 *     和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck! 输出描述:
 *     输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
 * @author Mr.Li
 */
public class FindSequence {

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        lists = new FindSequence().FindContinuousSequence(100);
        for (ArrayList<Integer> arrayList : lists) {
            for (Integer integer : arrayList) {
                System.out.print(integer + "\t");
            }
            System.out.println();
        }
    }

    /**
     * @param sum
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (sum <= 1) {
            return lists;
        }
        // 用两个数字begin和end分别表示序列的最小值和最大值
        // 首先将small初始化为1，big初始化为2.
        int small = 1;
        int big = 2;
        // 当small==(1+sum)/2的时候停止
        while (small != (1 + sum) / 2) {
            int curSum = sumOfList(small, big);
            if (curSum == sum) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = small; i <= big; i++) {
                    list.add(i);
                }
                lists.add(list);
                small++;
                big++;
            } else if (curSum < sum) {
                // 如果从small到big的和大于s，我们就从序列中去掉较小的值(即增大small)
                big++;
            } else {
                small++;
            }
        }
        return lists;
    }

    /**
     * @description 计算当前序列的和
     * @param head
     * @param leap
     * @return
     */
    private int sumOfList(int head, int leap) {
        int sum = head;
        for (int i = head + 1; i <= leap; i++) {
            sum += i;
        }
        return sum;
    }
}
