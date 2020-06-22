/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 *
 * <p>此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 *
 * <p>注意: 不能使用代码库中的排序函数来解决这道题。
 *
 * @author 梦境迷离
 * @time 2018-09-23
 */
public class Leetcode_75_Double_Pointer {

    /**
     * 两趟遍历
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count0++;
            }
            if (nums[i] == 1) {
                count1++;
            }
            if (nums[i] == 2) {
                count2++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (i < count0) {
                nums[i] = 0;
            } else if (i < count0 + count1) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
    }

    /**
     * 双指针
     *
     * <p>使用两个指针。一个用于插入0的位置，另一个用于插入2的位置。
     *
     * <p>迭代此数组，如果找到0，则将其值与零交换
     *
     * <p>如果找到了2，则用交换它的值和2
     *
     * <p>如果找到1，进入下一个位置，直到它超过2或落后于零。
     *
     * @param nums
     */
    public void sortColors2(int[] nums) {
        int p1 = 0, p2 = nums.length - 1, curr = 0;
        while (curr <= p2) {
            if (nums[curr] == 0) {
                nums[curr] = nums[p1];
                nums[p1] = 0;
                p1++;
            }
            if (nums[curr] == 2) {
                nums[curr] = nums[p2];
                nums[p2] = 2;
                p2--;
                curr--;
            }
            curr++;
        }
    }
}
