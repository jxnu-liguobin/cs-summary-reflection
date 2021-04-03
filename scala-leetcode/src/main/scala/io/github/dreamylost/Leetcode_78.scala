/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 78. 子集
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 * @author 梦境迷离
 * @since 2021/3/13
 * @version 1.0
 */
object Leetcode_78 extends App {

  /**
   * 656 ms,6.90%
   * 51.3 MB,62.07%
   */
  def subsets(nums: Array[Int]): List[List[Int]] = {
    var res = List.empty[List[Int]]
    nums.indices foreach { i: Int =>
      val combinations = nums.combinations(i + 1)
      while (combinations.hasNext) {
        res = res.appended(combinations.next().toList)
      }
    }
    res ++ List(List())
  }

  /**
   * 552 ms,62.07%
   * 50.9 MB,86.21%
   */
  def subsets_(nums: Array[Int]): List[List[Int]] = {
    var res = List.empty[List[Int]]
    var temp = List.empty[Int]
    def dfs(index: Int): Unit = {
      if (index == nums.length) {
        res = res.appended(List(temp: _*))
        return
      }
      // 考虑选择当前位置
      temp = temp.appended(nums(index))
      dfs(index + 1)
      // 考虑不选择当前位置
      temp = temp.init
      dfs(index + 1)
    }
    dfs(0)
    res
  }

  println(subsets(Array(1, 2, 3)))
  println(subsets_(Array(1, 2, 3)))
}
