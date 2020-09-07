/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 1502. 判断能否形成等差数列 (Medium)
  * 给你一个数字数组 arr 。
  *
  * 如果一个数列中，任意相邻两项的差总等于同一个常数，那么这个数列就称为 等差数列 。
  *
  * 如果可以重新排列数组形成等差数列，请返回 true ；否则，返回 false 。
  *
  * @see <a href="https://leetcode-cn.com/problems/can-make-arithmetic-progression-from-sequence/">leetcode-cn.com</a>
  */
object Leetcode_1502 extends App {
  def canMakeArithmeticProgression(arr: Array[Int]): Boolean = {
    if (arr.length < 3) false // 实际上，数学中要求等差数列至少要三项
    else {
      val sorted = arr.sorted
      val interval = sorted(1) - sorted(0)
      for (i <- sorted.indices.drop(1)) {
        if (sorted(i) - sorted(i - 1) != interval) return false
      }
      true
    }
  }

  assert(canMakeArithmeticProgression(Array(3, 5, 1)))
  assert(!canMakeArithmeticProgression(Array(1, 2, 4)))
}
