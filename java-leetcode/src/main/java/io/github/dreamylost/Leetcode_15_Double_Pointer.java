package io.github.dreamylost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * <p>不可以包含重复的三元组
 *
 * @author 梦境迷离
 * @time 2018-09-16
 */
public class Leetcode_15_Double_Pointer {

    /**
     * 分别是（1）寻找三个满足条件的元素（2）去重复
     *
     * <p>根据题目所给的条件，可以看出三个数字之和是定值，因此，当我们选取第一个数字num1后，问题变为寻找和为0-num1的两个数字。
     *
     * <p>可以观察到，因为数组是有序的，我们可以设置两个指针从两边同时选取
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return results;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复三元组
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            int target = -nums[i];
            twoSum(nums, left, right, target, results);
        }
        return results;
    }

    public static void twoSum(
            int[] nums, int left, int right, int target, List<List<Integer>> results) {
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                ArrayList<Integer> triple = new ArrayList<>();
                triple.add(-target);
                triple.add(nums[left]);
                triple.add(nums[right]);
                results.add(triple);
                left++;
                right--;
                // 跳过重复对
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }
            } else if (nums[left] + nums[right] < target) {
                left++;
            } else {
                right--;
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> ret = threeSum(new int[] {-1, 0, 1, 2, -1, -4});
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        ret.forEach(
                x -> {
                    String result =
                            x.stream()
                                    .map(j -> j.toString())
                                    .collect(Collectors.joining(",", "[", "]"));
                    stringBuilder.append(" " + result);
                    stringBuilder.append("\n");
                });
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }
}
