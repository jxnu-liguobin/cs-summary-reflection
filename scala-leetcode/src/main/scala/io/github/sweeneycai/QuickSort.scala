/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.util.Random

/**
 * 快速排序 -- 三数取中法递归实现（面试推荐写法）
 *
 * 可以避免排序时出现一边倒的情况。
 */
object QuickSort extends App {
  def quickSort(nums: Array[Int]): Array[Int] = {
    if (nums.length < 2) nums
    else if (nums.length == 2) nums.sorted
    else {
      val pivotIndex = between(1, nums.length - 1)
      val (a, b, c) = sort3(nums(0), nums(pivotIndex), nums(nums.length - 1))
      nums(0) = a
      nums(nums.length - 1) = c
      val less = nums.slice(0, pivotIndex).filter(_ < b) ++ nums
        .slice(pivotIndex + 1, nums.length)
        .filter(_ < b)
      val more = nums.slice(0, pivotIndex).filter(_ >= b) ++ nums
        .slice(pivotIndex + 1, nums.length)
        .filter(_ >= b)
      (quickSort(less) :+ b) ++ quickSort(more)
    }
  }

  def sort3(a: Int, b: Int, c: Int): (Int, Int, Int) = {
    val res = Array(a, b, c).sorted
    (res(0), res(1), res(2))
  }

  def between(start: Int, end: Int): Int = {
    val random = Random.nextInt(`end` - start)
    random + start
  }

  val test = Array(1, 2, 9, 6, 8, 4, 6, 2, 6, 8, 3)
  println(test.sorted.mkString("Array(", ", ", ")"))
  println(quickSort(test).mkString("Array(", ", ", ")"))
}
