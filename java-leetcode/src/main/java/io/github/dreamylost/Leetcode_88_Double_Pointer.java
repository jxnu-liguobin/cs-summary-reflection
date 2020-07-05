/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 归并两个有序数组，把归并结果存到第一个数组上。
 * @time 2018年4月7日
 */
public class Leetcode_88_Double_Pointer {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1; // 需要从尾开始遍历，否则在 nums1 上归并得到的值会覆盖还未进行归并比较的值
        int idx = m + n - 1;
        while (i >= 0 || j >= 0) {
            if (i < 0) nums1[idx] = nums2[j--];
            else if (j < 0) nums1[idx] = nums1[i--];
            else if (nums1[i] > nums2[j]) nums1[idx] = nums1[i--];
            else nums1[idx] = nums2[j--];
            idx--;
        }
    }
}
