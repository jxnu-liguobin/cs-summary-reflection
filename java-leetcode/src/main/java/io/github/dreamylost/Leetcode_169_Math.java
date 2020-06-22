/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.Arrays;

/**
 * 数组中出现次数多于 n / 2 的元素
 *
 * <p>169. Majority Element (Easy)
 *
 * <p>先对数组排序，最中间那个数出现次数一定多于 n / 2。
 *
 * @author 梦境迷离.
 * @time 2018年6月25日
 * @version v1.0
 */
public class Leetcode_169_Math {

    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 可以利用 Boyer-Moore Majority Vote Algorithm 来解决这个问题，使得时间复杂度为 O(N)。可以这么理解该算法：使用 cnt
     * 来统计一个元素出现的次数，当遍历到的元素和统计元素不相等时，令 cnt--。如果前面查找了 i 个元素，且 cnt == 0，说明前 i 个元素没有 majority，或者有
     * majority，但是出现的次数少于 i / 2，因为如果多于 i / 2 的话 cnt 就一定不会为 0。此时剩下的 n - i 个元素中，majority 的数目依然多于 (n -
     * i) / 2，因此继续查找就能找出 majority。
     *
     * <p>酷家乐二面
     *
     * <p>编程之美2，8题
     */
    public int majorityElement2(int[] nums) {
        int cnt = 0, majority = nums[0];
        for (int num : nums) {
            if (cnt == 0) { // 记录第一个元素
                majority = num; // 重点：当cnt=0的时候需要更新majority，相当继续在接下来的n-i个元素中查找个数大于(n-i)/2的元素
                cnt = 1;
            } else {
                // 当记录第二个元素的时候，如果元素不与majority相同，则cnt--，说明前面majority的次数少于一半
                // 因为如果多于一半，则cnt一定不为空，此时剩下的n-i元素中，majority数目艺人多余(n-i)/2，继续查找majority即可
                if (majority == num) {
                    cnt++;
                } else {
                    // 抵消2个不同的元素
                    cnt--;
                }
            }
        }
        return majority;
    }
}
