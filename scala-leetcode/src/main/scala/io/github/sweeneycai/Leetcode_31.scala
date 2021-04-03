/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 31. 下一个排列 (Medium)
 *
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须原地修改，只允许使用额外常数空间。
 */
object Leetcode_31 extends App {

  /**
   * 举个例子说明：
   * 对于 1 3 4 2，下一大的数字排列是 1 4 2 3
   * 首先找到从右边起第一个右边数字比左边数字大的位置 3
   * 接下来从右边找到第一个比 3 大的数字： 4
   * 接下来交换这两个数字： 1 4 3 2
   * 最后将 4 后面的数字反转： 1 4 2 3
   */
  def nextPermutation(nums: Array[Int]): Unit = {
    var index = nums.length - 2
    while (index >= 0 && nums(index + 1) <= nums(index)) index -= 1
    if (index >= 0) {
      var j = nums.length - 1
      while (j >= 0 && nums(j) <= nums(index)) j -= 1
      swap(nums, index, j)
    }

    reverse(nums, index + 1)
  }

  def swap(nums: Array[Int], i: Int, j: Int): Unit = {
    val tmp = nums(i)
    nums(i) = nums(j)
    nums(j) = tmp
  }

  def reverse(nums: Array[Int], start: Int): Unit = {
    var i = start
    var j = nums.length - 1
    while (i < j) {
      swap(nums, i, j)
      i += 1
      j -= 1
    }
  }

  val test1 = Array(1, 3, 4, 2)
  nextPermutation(test1)
  println(test1.mkString("Array(", ", ", ")"))

  val test2 = Array(1, 4, 3, 2)
  nextPermutation(test2)
  println(test2.mkString("Array(", ", ", ")"))
}
