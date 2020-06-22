/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.Arrays;
import java.util.Stack;

/**
 * 循环数组中比当前元素大的下一个元素
 *
 * <p>503. Next Greater Element II (Medium)
 *
 * <p>Input: [1,2,1] Output: [2,-1,2] Explanation: The first 1's next greater number is 2; The
 * number 2 can't find next greater number; The second 1's next greater number needs to search
 * circularly, which is also 2. 与 739. Daily Temperatures (Medium) 不同的是，数组是循环数组，并且最后要求的不是距离而是下一个元素。
 *
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_503_DataStructure {

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] next = new int[n];
        Arrays.fill(next, -1);
        Stack<Integer> pre = new Stack<>();
        for (int i = 0; i < n * 2; i++) { // 循环数组
            int num = nums[i % n];
            if (i < n) {
                pre.push(i);
            }
            while (!pre.isEmpty() && num > nums[pre.peek()]) {
                next[pre.pop()] = num;
            }
        }
        return next;
    }
}
