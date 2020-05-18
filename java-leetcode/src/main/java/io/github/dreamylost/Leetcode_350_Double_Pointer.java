package io.github.dreamylost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * <p>输入: nums1 = [1,2,2,1], nums2 = [2,2] 输出: [2,2]
 *
 * @author 梦境迷离
 * @time 2018-09-23
 */
public class Leetcode_350_Double_Pointer {

    /**
     * using Java HashMap
     *
     * <p>O(n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {

        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            if (map.containsKey(nums1[i])) map.put(nums1[i], map.get(nums1[i]) + 1);
            else map.put(nums1[i], 1);
        }

        for (int i = 0; i < nums2.length; i++) {
            if (map.containsKey(nums2[i]) && map.get(nums2[i]) > 0) {
                result.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i]) - 1);
            }
        }

        int[] r = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            r[i] = result.get(i);
        }

        return r;
    }

    /**
     * 如果给定的数组已经排好序
     *
     * <p>否则需要排序NlogN
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        int p1 = 0;
        int p2 = 0;
        int length1 = nums1.length;
        int length2 = nums2.length;
        int[] result = new int[Math.min(length1, length2)];
        int counter = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        while (p1 < length1 && p2 < length2) {
            if (nums1[p1] == nums2[p2]) {
                result[counter] = nums1[p1];
                p1++;
                p2++;
                counter++;
            } else if (nums1[p1] > nums2[p2]) {
                p2++;
            } else {
                p1++;
            }
        }
        return Arrays.copyOfRange(result, 0, counter);
    }
}
