/* All Contributors (C) 2020 */
package io.github.wkk.everyday.junly;

/**
 * @author kongwiki@163.com
 * @since 2020/7/31下午8:54
 */
public class MagicIndexLCCI {
    public int findMagicIndex(int[] nums) {
        int n = nums.length;
        if (nums == null || n == 0) {
            return -1;
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] == i) {
                return nums[i];
            }
        }
        return -1;
    }

    public int findMagicIndexII(int[] nums) {
        if (nums[0] == 0) return 0;

        int l = 0, r = nums.length - 1;

        if (nums[0] > 0) {
            while (l < r) {
                int mid = l + (r - l + 1) / 2; // 取右边界
                if (nums[mid] < mid) {
                    r = mid - 1;
                } else if (nums[mid] > mid) {
                    l++; // 没办法，一点点移动吧
                } else {
                    return mid; // 因 l++ 第一个遇到的
                }
            }
        } else {
            while (l < r) {
                int mid = l + (r - l + 1) / 2;
                if (nums[mid] > mid) {
                    r = mid - 1;
                } else if (nums[mid] < mid) {
                    l++;
                } else {
                    return mid;
                }
            }
        }
        return -1;
    }
}
