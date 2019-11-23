package io.github.dreamylost.tooffer;

import java.util.ArrayList;

/**
 * 输出所有和为S的连续正数序列。
 * 序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
 */
public class T41 {

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        int phigh = 2;
        int plow = 1;
        while (phigh > plow) {
            int cur = (phigh + plow) * (phigh - plow + 1) / 2;
            if (cur < sum) {
                phigh++;
            }
            if (cur == sum) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = plow; i <= phigh; i++) {
                    arrayList.add(i);
                }
                arrayLists.add(arrayList);
                plow++;
            }
            if (cur > sum) {
                plow++;
            }
        }
        return arrayLists;
    }
}
