/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
 * 448. 找到所有数组中消失的数字 (Easy)
 *
 * 给定一个范围在 1 ≤ a[i] ≤ n (n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
 *
 * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
 *
 * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
 *
 * @see <a href="https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/">leetcode-cn.com</a>
 */
object Leetcode_448 extends App {
  def findDisappearedNumbers(nums: Array[Int]): List[Int] = {
    val res: ListBuffer[Int] = ListBuffer.empty
    // 遍历一次，记录出现的数字，也就是将对应下标的值设为 负数
    for (i <- nums.indices) {
      val tmp = math.abs(nums(i)) - 1
      if (nums(tmp) > 0) nums(tmp) = -1 * nums(tmp)
    }
    // 再遍历一次，找出值为 正数 的下标
    for (i <- nums.indices) {
      if (nums(i) > 0) res.append(i + 1)
    }
    res.toList
  }

  println(findDisappearedNumbers(Array()))
  println(findDisappearedNumbers(Array(4, 3, 2, 7, 8, 2, 3, 1)))
}
