/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 一个数 x 的开方 sqrt 一定在 0 ~ x 之间，并且满足 sqrt == x / sqrt 。可以利用二分查找在 0 ~ x 之间查找 sqrt。
 * @time 2018年3月30日
 */
public class Leetcode_69 {

    public int mySqrt(int x) {
        if (x <= 1) return x;
        int l = 1, h = x;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            int sqrt = x / mid;
            if (sqrt == mid) return mid;
            else if (sqrt < mid) h = mid - 1;
            else l = mid + 1;
        }
        return h;
    }
}
