/* All Contributors (C) 2020 */
package io.github.dreamylost;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 找出数组中的乘积最大的三个数
 *
 * <p>628. Maximum Product of Three Numbers (Easy)
 *
 * <p>Input: [1,2,3,4] Output: 24
 *
 * @author 梦境迷离.
 * @time 2018年6月29日
 * @version v1.0
 */
public class Leetcode_628_Math {

    /** @param args */
    public static void main(String[] args) {

        int[] arr = {1, 2, 3, -1, -99};
        int ret = Leetcode_628_Math.maximumProduct3(arr);
        System.out.println(ret);
    }

    /** 粗暴 排序需要NlogN */
    public static int maximumProduct(int[] nums) {
        if (nums.length < 3) return 0;
        Arrays.sort(nums);
        int len = nums.length - 1; // 排序后的最大值就是最后三个数的乘积/或者是前面2个负数和后面一个正数的乘积
        return Math.max(
                nums[0] * nums[1] * nums[len],
                Math.max(nums[0] * nums[1] * nums[2], nums[len] * nums[len - 1] * nums[len - 2]));
    }

    public static int maximumProduct2(int[] nums) {
        // 3个正数，或者2个负数*1个正数
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int n : nums) {
            // 寻找最大3个数
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }
            // 寻找最小的两个数，负数绝对值越大实际值越小
            if (n < min1) {
                min2 = min1;
                min1 = n;
                // System.out.println("min1:" + min1 + " ");
            } else if (n < min2) {
                min2 = n;
                // System.out.println("min2:" + min2 + " ");

            }
        }
        return Math.max(max1 * max2 * max3, max1 * min1 * min2);
    }

    /**
     * 使用堆存3个最大数，和2个最小数
     *
     * <p>最大的值用小堆(逆序)，找最小的值用大堆(升序)
     */
    public static int maximumProduct3(int[] nums) {
        PriorityQueue<Integer> poheap = new PriorityQueue<>(); // 优先级队列，默认小堆
        PriorityQueue<Integer> neheap = new PriorityQueue<>(Collections.reverseOrder()); // 反序，大堆
        for (int num : nums) {
            poheap.offer(num);
            neheap.offer(num);
            if (poheap.size() > 3) {
                poheap.poll(); // 每次去除小堆顶【最小堆的堆顶就是最小值】，剩下三个为最大值的三个数
            }
            if (neheap.size() > 2) {
                neheap.poll(); // 每次去除大堆顶【最大堆的堆顶就是最大值】，剩下2个为最小的2个数
            }
        }
        int c1 = 1;
        int max = 0;
        // 最大堆的三个大数相乘
        while (!poheap.isEmpty()) {
            max = poheap.poll();
            c1 *= max;
        }
        // 最小的2个与最大值相乘
        while (!neheap.isEmpty()) {
            max *= neheap.poll();
        }
        return Math.max(c1, max);
    }
}
