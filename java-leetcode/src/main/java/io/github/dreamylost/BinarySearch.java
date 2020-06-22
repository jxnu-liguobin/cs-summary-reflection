/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 二分查找思想简单，但是在实现时有一些需要注意的细节： 在计算 mid 时不能使用 mid = (l + h) / 2 这种方式，因为 l + h
 *     可能会导致加法溢出，应该使用 mid = l + (h - l) / 2 。 对 h 的赋值和循环条件有关，当循环条件为 l <= h 时，h = mid - 1；当循环条件为 l <
 *     h 时，h = mid。 解释如下：在循环条件为 l <= h 时，如果 h = mid，会出现循环无法退出的情况，例如 l = 1，h = 1，此时 mid 也等于
 *     1，如果此时继续执行 h = mid ，那么就会无限循环；在循环条件为 l < h ，如果 h = mid - 1，会错误跳过查找的数，例如对于数组 1,2,3 ，要查找 1 ，最开始
 *     l = 0，h = 2，mid = 1，判断 key < arr[mid] 执行 h = mid - 1 = 0，此时循环退出，直接把查找的数跳过了。 l 的赋值一般都为 l = mid
 *     + 1。
 * @time 2018年3月30日
 */
public class BinarySearch {
    public int search(int key, int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (key == arr[mid]) return mid;
            if (key < arr[mid]) right = mid - 1;
            else left = mid + 1;
        }
        return -1;
    }
}
