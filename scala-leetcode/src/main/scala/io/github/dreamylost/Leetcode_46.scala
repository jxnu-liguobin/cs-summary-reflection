/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import scala.collection.mutable.ListBuffer

/**
 * 46. 全排列
 * @author 梦境迷离
 * @since 2021/4/3
 * @version 1.0
 */
object Leetcode_46 extends App {
  object Solution {

    /**
     * 604 ms,38.89%
     * 53.5 MB,36.11%
     */
    def permute(nums: Array[Int]): List[List[Int]] = {
      nums.permutations.toList.map(_.toList)
    }

    /**
     * 556 ms，88.89%
     * 54.6 MB，5.55%
     */
    def permute2(nums: Array[Int]): List[List[Int]] = {
      def backtrack(
          res: ListBuffer[List[Int]],
          tmp: ListBuffer[Int],
          visited: Array[Boolean]
      ): Unit = {
        if (tmp.size == nums.length) {
          res.append(tmp.toList)
          return
        }
        for (i <- nums.indices) {
          if (!visited(i)) {
            visited(i) = true
            tmp.append(nums(i))
            backtrack(res, tmp, visited)
            visited(i) = false
            tmp.remove(tmp.length - 1)
          }
        }
      }
      val res = ListBuffer[List[Int]]()
      backtrack(res, ListBuffer(), new Array[Boolean](nums.length))
      res.toList
    }

  }

  val r = Solution.permute(Array(1, 2, 3))
  val r2 = Solution.permute2(Array(1, 2, 3))
  println(r)
  println(r2)
}
