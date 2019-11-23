package io.github.dreamylost;

/**
 * 42. 接雨水
 * <p>
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_42_Double_Pointer {


    /**
     * 其基本思想是在高度的左右两端设置两个指针L和R
     * 然后我们得到这些指针的最小高度（minHeight）因为水的高度不能高于它
     * 如果即将到来的水位低于最小高度，那么它将保持一定的水位。
     * 填充水直到我们遇到一些“屏障”（高度大于minHeight）
     * 并更新l和r以在新的间隔中重复这个过程。
     */
    public int trap(int[] height) {
        int n = height.length, l = 0, r = n - 1, water = 0, minHeight = 0;
        while (l < r) {
            while (l < r && height[l] <= minHeight)
                water += minHeight - height[l++];
            while (r > l && height[r] <= minHeight)
                water += minHeight - height[r--];
            minHeight = Math.min(height[l], height[r]);
        }
        return water;
    }
}
