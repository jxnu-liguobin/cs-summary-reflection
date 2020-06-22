/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.Arrays;

/**
 * 881. 救生艇
 *
 * <p>第 i 个人的体重为 people[i]，每艘船可以承载的最大重量为 limit。
 *
 * <p>每艘船最多可同时载两人，但条件是这些人的重量之和最多为 limit。
 *
 * <p>返回载到每一个人所需的最小船数。(保证每个人都能被船载)。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_881_Double_Pointer {

    /**
     * 如果左边和右边的总和比小船大，只把右边的放在船上。
     *
     * <p>否则，两者都可以放在船上。
     *
     * @param people
     * @param limit
     * @return
     */
    public int numRescueBoats(int[] people, int limit) {
        if (people == null) {
            return 0;
        }
        Arrays.sort(people);
        int count = 0, left = 0, right = people.length - 1;
        while (left <= right) {
            // 和小于等于的时候需要左移
            if (people[left] + people[right] <= limit) {
                left++;
            } // 大于的时候需要右移动，
            right--;
            count++;
        }
        return count;
    }
}
