/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 739. 每日温度
 *
 * @author 梦境迷离
 * @since 2021/4/5
 * @version 1.0
 */
object Leetcode_739 extends App {

  object Solution {

    /**
     * 2532 ms,23.81%
     * 58.6 MB,100.00%
     */
    def dailyTemperatures(T: Array[Int]): Array[Int] = {
      import scala.util.control.Breaks.break
      import scala.util.control.Breaks.breakable
      val res = new Array[Int](T.length)
      for (i <- 0 until T.length - 1) {
        breakable {
          for (j <- i until T.length) {
            if (T(j) > T(i)) {
              res(i) = j - i
              break
            }
          }
        }
      }
      res
    }

    /**
     * 单调递减栈
     * 832 ms,90.48%
     * 58.7 MB,95.24%
     */
    def dailyTemperatures2(T: Array[Int]): Array[Int] = {
      val stack = new java.util.Stack[Int]
      val res = new Array[Int](T.length)
      for (i <- T.indices) {
        while (!stack.isEmpty && T(i) > T(stack.peek)) {
          val temp = stack.pop
          res(temp) = i - temp
        }
        stack.push(i)
      }
      res
    }
  }

  val res = Solution.dailyTemperatures(Array(73, 74, 75, 71, 69, 72, 76, 73))
  val res2 = Solution.dailyTemperatures2(Array(73, 74, 75, 71, 69, 72, 76, 73))
  println(res.toSeq)
  println(res2.toSeq)
}
