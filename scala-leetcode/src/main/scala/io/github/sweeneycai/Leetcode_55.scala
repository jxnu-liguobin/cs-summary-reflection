/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 55. 跳跃游戏 (Medium)
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个位置。
 */
object Leetcode_55 extends App {
  def canJump(nums: Array[Int]): Boolean = {
    var max = 0
    for (i <- nums.indices) {
      if (max < i) return false // 不能到达当前节点
      else if (max >= nums.length - 1) return true // 可以到达最后一个节点
      else if (max >= i) max = math.max(nums(i) + i, max) // 每到达一个节点，就更新当前能走到的最远距离
    }

    max >= nums.length - 1 // make compiler happy
  }

  assert(!canJump(Array(3, 2, 1, 0, 4)))
  assert(canJump(Array(5, 3, 1, 0, 4)))
  assert(canJump(Array(1, 1, 1, 1)))
  assert(!canJump(Array(0, 1, 2)))
  assert(canJump(Array(3, 0, 8, 2, 0, 0, 1)))
  //  assert(!canJump(Array()))
}
