/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai
import scala.collection.mutable.ListBuffer

/**
  *  15.三数之和 (Medium)
  *
  * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，
  * 使得a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
  *
  * 注意：答案中不可以包含重复的三元组。
  */
object Leetcode_15 {
  def threeSum(nums: Array[Int]): List[List[Int]] = {
    val sorted = nums.sorted
    val listBuffer: ListBuffer[List[Int]] = ListBuffer.empty
    var i = 0
    while (i < sorted.length - 2) {
      var j = i + 1
      var k = sorted.length - 1
      while (j < k) {
        if (sorted(i) + sorted(j) + sorted(k) > 0) {
          k -= 1
          while (k > j && sorted(k) == sorted(k + 1)) k -= 1
        } else if (sorted(i) + sorted(j) + sorted(k) < 0) {
          j += 1
          while (j < k && sorted(j) == sorted(j - 1)) j += 1
        } else {
          listBuffer.append(List(sorted(i), sorted(j), sorted(k)))
          j += 1
          while (j < k && sorted(j) == sorted(j - 1)) j += 1
          k -= 1
          while (k > j && sorted(k) == sorted(k + 1)) k -= 1
        }

      }
      i += 1
      while (i < sorted.length - 2 && sorted(i) == sorted(i - 1)) i += 1
    }
    listBuffer.toList
  }

  def main(args: Array[String]): Unit = {
    println(threeSum(Array(0, 0, 0)))
  }
}
