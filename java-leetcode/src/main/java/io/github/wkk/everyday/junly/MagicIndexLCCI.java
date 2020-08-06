/* All Contributors (C) 2020 */
package io.github.wkk.everyday.junly;

/**
 * @author kongwiki@163.com
 * @since 2020/7/31下午8:54
 */
public class MagicIndexLCCI {
    public int findMagicIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == i) {
                return nums[i];
            }
        }
        return -1;
    }

    public int findMagicIndexII(int[] nums) {
        if (nums[0] == 0) {
            return 0;
        }

        int l = 0, r = nums.length - 1;

        if (nums[0] > 0) {
            while (l < r) {
                // 取右边界
                int mid = l + (r - l + 1) / 2;
                if (nums[mid] < mid) {
                    r = mid - 1;
                } else if (nums[mid] > mid) {
                    l++;
                } else {
                    return mid;
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
