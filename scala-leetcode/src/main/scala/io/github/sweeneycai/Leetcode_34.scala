/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置 (Medium)
 *
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是O(log n) 级别。
 */
object Leetcode_34 extends App {
  def searchRange(nums: Array[Int], target: Int): Array[Int] = {
    // 二分查找思想
    var left = 0
    var right = nums.length - 1
    var leftBound = -1
    var rightBound = -1
    // 当左右指针指向的数字相等时提前结束
    while (right >= 0 && left <= right && nums(left) != nums(right)) {
      val mid = (left + right) / 2
      if (nums(mid) > target) right = mid - 1
      else if (nums(mid) < target) left = mid + 1
      else {
        leftBound = mid
        rightBound = mid
        left = -1
        right = -1
      }
    }
    // 当nums(left) == nums(right)的时候对左边界和右边界进行更新
    if (right >= 0 && nums(left) == nums(right)) {
      leftBound = left
      rightBound = right
    }
    if ((leftBound == -1 && rightBound == -1) || nums(leftBound) != target) {
      Array(-1, -1)
    } else {
      while (leftBound > 0 && nums(leftBound) == nums(leftBound - 1)) leftBound -= 1
      while (rightBound < nums.length - 1 && nums(rightBound) == nums(rightBound + 1))
        rightBound += 1
      Array(leftBound, rightBound)
    }
  }

  println(searchRange(Array(5, 7, 7, 8, 8, 10), 6).mkString("Array(", ", ", ")"))
  println(searchRange(Array(0), 0).mkString("Array(", ", ", ")"))
  println(searchRange(Array(1, 1, 2), 1).mkString("Array(", ", ", ")"))
  println(searchRange(Array(), 1).mkString("Array(", ", ", ")"))
  println(searchRange(Array(1, 2, 3), 1).mkString("Array(", ", ", ")"))
}
