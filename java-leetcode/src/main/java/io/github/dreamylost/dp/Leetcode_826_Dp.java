/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.dp;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * 826. 安排工作以达到最大收益
 *
 * <p>有一些工作：difficulty[i] 表示第i个工作的难度，profit[i]表示第i个工作的收益。
 * 现在我们有一些工人。worker[i]是第i个工人的能力，即该工人只能完成难度小于等于worker[i]的工作。 每一个工人都最多只能安排一个工作，但是一个工作可以完成多次。
 * 举个例子，如果3个工人都尝试完成一份报酬为1的同样工作，那么总收益为 $3。如果一个工人不能完成任何工作，他的收益为 $0 。 我们能得到的最大收益是多少？
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_826_Dp {

    /** 难度和利润作为工作 */
    class Pair {

        int diff;
        int prof;

        public Pair(int diff, int prof) {
            this.diff = diff;
            this.prof = prof;
        }
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        Pair[] combo = new Pair[difficulty.length];
        for (int i = 0; i < combo.length; i++) combo[i] = new Pair(difficulty[i], profit[i]);
        Arrays.sort(combo, (a, b) -> (a.diff - b.diff)); // 以难度排序
        TreeMap<Integer, Integer> preMax = new TreeMap<>();
        int max = 0;
        for (Pair pair : combo) {
            max = Math.max(max, pair.prof);
            preMax.put(pair.diff, max); // 难度m的最大利润n
        }
        int result = 0;
        for (int w : worker) {
            Integer lower = preMax.floorKey(w); // floorKey用于返回的最大键小于或等于给定的键,或null
            if (lower != null) result += preMax.get(lower);
        }
        return result;
    }
}
