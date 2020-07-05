/* All Contributors (C) 2020 */
package io.github.dreamylost;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author 梦境迷离
 * @description Kth Element 求第K大的元素
 * @time 2018年4月8日
 */
public class Leetocde_215_Sort {
    /**
     * @author 梦境迷离
     * @description 排序 ：时间复杂度 O(NlogN)，空间复杂度 O(1)
     * @time 2018年4月8日
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        // 返回已经排序的数组中的元素 nums[nums.length - k]，就是第K大的
        return nums[nums.length - k];
    }

    /**
     * @author 梦境迷离
     * @description 堆排序 ：时间复杂度 O(OlogK)，空间复杂度 O(K)。
     * @time 2018年4月8日
     */
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val : nums) {
            pq.add(val);
            // 保证堆中有K个元素
            if (pq.size() > k) {
                // 每次超过K个 就删除最小的元素
                pq.poll();
            }
        }
        // 此时堆顶就是一个第K大的元素
        return pq.peek();
    }

    /**
     * @author 梦境迷离
     * @description 快速选择 ：时间复杂度 O(N)，空间复杂度 O(1)
     * @time 2018年4月8日
     */
    public int findKthLargest2(int[] nums, int k) {
        k = nums.length - k;
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int j = partition(nums, l, h);
            if (j == k) break;
            if (j < k) l = j + 1;
            else h = j - 1;
        }
        return nums[k];
    }

    /**
     * @author 梦境迷离
     * @description 选择排序，最大的在前面，当j==k时，nums[k]就是第K大的数
     * @time 2018年4月8日
     */
    private int partition(int[] a, int l, int h) {
        int i = l, j = h + 1;
        while (true) {
            while (i < h && a[++i] < a[l]) ;
            while (j > l && a[l] < a[--j]) ;
            if (i >= j) break;
            swap(a, i, j);
        }
        swap(a, l, j);
        return j;
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
