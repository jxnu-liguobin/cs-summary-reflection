/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 39. 组合总和
 *
 * @author 梦境迷离
 * @since 2021/4/3
 * @version 1.0
 */
object Leetcode_39 extends App {

  object Solution {

    /**
     * 回溯，内部循环减枝
     * 608 ms,96.43%
     * 53.3 MB,92.86%
     */
    def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
      import scala.collection.mutable.ListBuffer
      val nums = candidates.sorted
      val res = ListBuffer[List[Int]]()

      def helper(candidate: ListBuffer[Int], index: Int, sum: Int): Unit = {
        if (index == nums.length) return
        if (sum == 0) {
          res.append(candidate.toList)
          return
        }
        if (sum < nums(index)) return
        candidate.append(nums(index))
        // 可以重复取同一个数字 所以下一个坐标还是当前的index
        helper(candidate, index, sum - nums(index))
        candidate.remove(candidate.size - 1)
        helper(candidate, index + 1, sum)
      }

      helper(ListBuffer(), 0, target)
      res.toList
    }
  }

  val res = Solution.combinationSum(Array(2, 3, 6, 7), 7)
  println(res)

}
