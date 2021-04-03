/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
 * 491. 递增子序列 (Medium)
 *
 * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
 */
object Leetcode_491 extends App {
  // 递归枚举
  def findSubsequences(nums: Array[Int]): List[List[Int]] = {
    val listBuffer: ListBuffer[List[Int]] = ListBuffer.empty

    def dfs(cur: Int, last: Int, hist: List[Int]): Unit = {
      if (cur == nums.length) {
        if (hist.length >= 2) listBuffer.append(hist)
        return
      }
      // 只有当前指向的元素大于最后一个元素时，才可以继续添加
      if (nums(cur) >= last) dfs(cur + 1, nums(cur), hist :+ nums(cur))
      // 这一步只是将当前节点跳过，其中last存储了当前添加的最大节点
      // 也就是说，对于每一个节点而言，要么我们将其添加到最终队列中，要么就将其跳过
      // 所以对于 4，6，7，3，7 序列，
      // 第一次搜索，我们得到了 4 6 7 7
      // 接下来会跳过第一个7，得到 4 6 7，以此类推，这样可以避免产生重复序列
      if (nums(cur) != last) dfs(cur + 1, last, hist)
    }

    dfs(0, Int.MinValue, List.empty)
    listBuffer.toList
  }

  println(findSubsequences(Array(4, 6, 7, 3, 7)))
}
