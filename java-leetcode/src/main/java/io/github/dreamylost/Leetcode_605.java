/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 假设你有一个很长的花坛，一部分地块种植了花，另一部分却没有。
 *     可是，花卉不能种植在相邻的地块上，它们会争夺水源，两者都会死去。给定一个花坛（表示为一个数组包含0和1，其中0表示没种植花， 1表示种植了花），和一个数 n
 *     。能否在不打破种植规则的情况下种入 n 朵花？能则返回True，不能则返回False。 示例1: 输入: flowerbed = [1,0,0,0,1], n = 1 输出: True
 *     示例 2: 输入: flowerbed = [1,0,0,0,1], n = 2 输出: False 注意: 数组内已种好的花不会违反种植规则。 输入的数组长度范围为 [1,
 *     20000]。 n 是非负整数，且不会超过输入数组的大小。
 * @time 2018年4月1日
 */
public class Leetcode_605 {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int cnt = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            // 已经是1，代表种了，跳过
            if (flowerbed[i] == 1) {
                continue;
            }
            int pre, next = 0;
            if (i == 0) {
                // 第一个数的前面设置为0
                pre = 0;
            } else {
                // 保存前面一个元素
                pre = flowerbed[i - 1];
            }
            // 最后一个元素的后面设置为0
            if (i == flowerbed.length - 1) {
                next = 0;
            } else {
                // 保存后面一个元素
                i = flowerbed[i + 1];
            }
            if (pre == 0 && next == 0) {
                cnt++;
                flowerbed[i] = 1;
            }
        }
        return cnt >= n;
    }
}
