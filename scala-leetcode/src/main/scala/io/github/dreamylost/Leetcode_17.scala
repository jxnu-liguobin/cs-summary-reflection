/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 17. 电话号码的字母组合
 *
 * @author 梦境迷离
 * @since 2021/4/4
 * @version 1.0
 */
object Leetcode_17 extends App {

  object Solution {

    /**
     * 利用hash映射和index回溯
     * 520 ms,75.00%
     * 50.8 MB,37.50%
     */
    def letterCombinations(digits: String): List[String] = {
      import scala.collection.mutable.ListBuffer
      if (digits == null || digits.isEmpty) return Nil
      lazy val mapping = Map(
        '2' -> "abc",
        '3' -> "def",
        '4' -> "ghi",
        '5' -> "jkl",
        '6' -> "mno",
        '7' -> "pqrs",
        '8' -> "tuv",
        '9' -> "wxyz"
      )
      val res = ListBuffer[String]()

      def helper(tmp: ListBuffer[Char], index: Int): Unit = {
        if (tmp.length == digits.length) {
          res.append(tmp.mkString)
          return
        }
        val t = mapping(digits(index))
        for (c <- t.toCharArray) {
          tmp.append(c)
          helper(tmp, index + 1)
          tmp.remove(tmp.length - 1)
        }
      }

      helper(ListBuffer(), 0)
      res.toList
    }
  }

  val res = Solution.letterCombinations("23")
  println(res)

}
