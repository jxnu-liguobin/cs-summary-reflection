package io.github.dreamylost

/**
 * 找出数组中重复的数，数组值在 [1, n] 之间
 *
 * 287. Find the Duplicate Number (Medium)
 *
 * 要求不能修改数组，也不能使用额外的空间。
 *
 * @author 梦境迷离
 * @time 2018年7月16日
 * @version v1.0
 */
object Leetcode_287_Array {

    def main(args: Array[String]) {
        val nums = Array(1, 2, 3, 3, 4, 5, 6)
        val ret = Leetcode_287_Array.findDuplicate2(nums)
        print(ret)

    }

    /**
     * 二分查找解法：
     */
    val findDuplicate = (nums: Array[Int]) ⇒ {
        var l = 1
        var h = nums.length - 1
        while (l <= h) {
            val mid = l + (h - l) / 2
            var cnt = 0
            for (i <- 0 until nums.length) {
                if (nums(i) <= mid)
                    cnt = cnt + 1
            }
            if (cnt > mid)
                h = mid - 1
            else
                l = mid + 1
        }
        l
    }

    /**
     * 双指针解法，类似于有环链表中找出环的入口
     *
     * 有环找入口：慢指针走过的路程是：
     *
     * S_slow = |OA| +|AB| = x + y.
     * 快指针走过的路程是：
     * S_fast =  |OA| + |AB| + |BA| + |AB| = x + y + z + y.
     * 又因为快指针的速度是慢指针的两倍，所以在相同时间内快指针走过的路程是慢指针的两倍，所以
     * S_fast = 2 * S_slow
     * 即2(x + y) = x + y + z + y.
     * 求得z = x.
     * 所以说明
     * |BA| = |OA|.
     * 所以在两个指针相遇后，将慢指针移到O点起始位置，即链表头指针位置，快指针仍然在B点。
     * 然后它们一起向前移动，每次移动一个位置，由于|BA| = |OA|, 所以他们最终肯定会在A点相遇，A点这个相遇点就是环的入口点。
     */
    def findDuplicate2(nums: Array[Int]): Int = {
        var slow = nums(0)
        var fast = nums(nums(0))
        while (slow != fast) {
            slow = nums(slow)
            fast = nums(nums(fast))
        }
        fast = 0
        while (slow != fast) {
            slow = nums(slow)
            fast = nums(fast)
        }
        slow
    }
}