/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.ArrayList;
import java.util.List;

/**
 * 448. Find All Numbers Disappeared in an Array
 *
 * <p>同645
 *
 * @author 梦境迷离.
 * @time 2018年7月15日
 * @version v1.0
 */
public class Leetcode_448_Array {

    public static void main(String[] args) {
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> ret = Leetcode_448_Array.findDisappearedNumbers(nums);
        ret.stream().forEachOrdered(System.out::print);
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                list.add(i + 1);
            }
        }
        return list;
    }

    /** 交换 */
    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
