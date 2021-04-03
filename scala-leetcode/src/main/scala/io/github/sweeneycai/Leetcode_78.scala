/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
 * 78. 子集 (Medium)
 *
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *
 * 此题可参考：491. 递增子序列 (Medium)
 *
 * @see <a href="https://leetcode-cn.com/problems/subsets/">leetcode-cn.com</a>
 */
object Leetcode_78 extends App {

  /**
   * nums 中不包含重复元素，则不需要剪枝判断
   */
  def subsets1(nums: Array[Int]): List[List[Int]] = {
    val sorted = nums.sorted
    val res: ListBuffer[List[Int]] = ListBuffer(List())
    def dfs(index: Int, path: List[Int]): Unit = {
      if (index == sorted.length) return
      res.append(path :+ sorted(index))
      dfs(index + 1, path :+ sorted(index))
      dfs(index + 1, path)
    }
    dfs(0, List.empty)
    res.toList
  }

  /**
   * nums 中包含重复元素，则需要剪枝判断。
   */
  def subsets2(nums: Array[Int]): List[List[Int]] = {
    val sorted = nums.sorted
    val res: ListBuffer[List[Int]] = ListBuffer.empty
    // 此处使用 last 记录上一个添加的元素
    def dfs(index: Int, last: Int, path: List[Int]): Unit = {
      if (index == sorted.length) return
      if (sorted(index) == last) {
        // 重复元素，之前处理过就剪枝
        dfs(index + 1, last, path)
      } else {
        // 非重复元素，之前未处理过，那么我们可以选择处理这个元素，或者跳过这个元素
        // 也可以使用回溯的方法解决
        res.append(path :+ sorted(index))
        dfs(index + 1, sorted(index), path :+ sorted(index))
        dfs(index + 1, sorted(index), path)
      }
    }
    dfs(0, Int.MinValue, List.empty)
    res.toList
  }

  println(subsets1(Array(1, 2, 3)))
}
