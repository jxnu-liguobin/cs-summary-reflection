/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 *  22. 括号生成
 * @author 梦境迷离
 * @since 2021/4/3
 * @version 1.0
 */
object Leetcode_22 extends App {

  object Solution {

    /**
     * 回溯
     * 552 ms，27.27%
     * 49.3 MB，77.27%
     */
    def generateParenthesis(n: Int): List[String] = {
      //1.结果，临时值，临界条件
      def helper(
          res: scala.collection.mutable.ListBuffer[String],
          str: String,
          l: Int,
          r: Int
      ): Unit = {
        if (l > n || r > n || r > l) return
        if (l == n && r == n) {
          res.append(str)
          return
        }
        helper(res, str + "(", l + 1, r)
        helper(res, str + ")", l, r + 1)
      }
      val res = scala.collection.mutable.ListBuffer[String]()
      helper(res, "", 0, 0)
      res.toList
    }
  }

  val res = Solution.generateParenthesis(3)
  println(res)

}
