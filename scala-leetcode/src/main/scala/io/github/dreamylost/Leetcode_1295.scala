/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。
 *
 * @author 梦境迷离
 * @since 2020-01-01
 * @version v1.0
 */
object Leetcode_1295 extends App {

  println(findNumbers(Array(12, 345, 2, 6, 7896)))

  def findNumbers(nums: Array[Int]): Int = {

    val isEvenNum = (n: Int) => {
      var i = 0
      var j = n
      while (j > 0) {
        i += 1
        j = j / 10
      }
      if (i % 2 == 0) true else false
    }
    //_.toString.length == 2 (滑稽)
    nums.count(isEvenNum)
  }
}
