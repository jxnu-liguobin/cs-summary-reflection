package io.github.dreamylost;

import java.util.Arrays;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 * <p>
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 * <p>
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 *
 * @author 梦境迷离
 * @time 2018-09-25
 */
public class Leetcode_16_Double_Pointer {

    /**
     * 类似于3数和问题，使用3个指针来指向当前元素、下一个元素和最后一个元素。
     * <p>
     * 如果总和小于目标，意味着我们必须添加一个更大的元素，所以下一个元素移动到下一个元素。
     * 如果总和较大，则意味着我们必须添加一个较小的元素，以便最后一个元素移动到倒数第二个元素。
     * <p>
     * 每次比较和和目标之间的绝对值，如果它小于迄今为止的最小绝对值，那么就用它替换结果，否则继续迭代。
     *
     * @param num
     * @param target
     * @return
     */
    public int threeSumClosest(int[] num, int target) {
        int result = num[0] + num[1] + num[num.length - 1];
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            //nums[i]=当前元素，num[start]=下一个元素，nums[end]最后的元素
            int start = i + 1, end = num.length - 1;
            while (start < end) {
                int sum = num[i] + num[start] + num[end];
                if (sum > target) {
                    end--;
                } else {
                    start++;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }

}
