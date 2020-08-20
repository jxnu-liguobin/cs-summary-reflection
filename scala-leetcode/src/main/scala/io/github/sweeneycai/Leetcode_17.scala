/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
  * 17. 电话号码的字母组合 (Medium)
  *
  * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
  *
  * 给出数字到字母的映射与电话按键相同。注意 1 不对应任何字母。
  */
object Leetcode_17 {

  /**
    * 利用了`flatMap`的特性
    */
  def letterCombinations(digits: String): List[String] = {
    val lookup = Array(
      "abc",
      "def",
      "ghi",
      "jkl",
      "mno",
      "pqrs",
      "tuv",
      "wxyz"
    )

    def gen(list: List[Char]): IndexedSeq[String] =
      list match {
        case Nil => IndexedSeq("")
        case ::(head, next) =>
          for {
            i <- lookup(head.toInt - 50) // 减去48为真实值，此处需要额外减2
            j <- gen(next)
          } yield i + j
      }

    if (digits.length == 0) List.empty[String]
    else gen(digits.toCharArray.toList).toList
  }

  /**
    * 官方解答
    */
  def officialSolution(digits: String): List[String] = {
    val lookUp: Map[String, String] =
      Map(
        "2" -> "abc",
        "3" -> "def",
        "4" -> "ghi",
        "5" -> "jkl",
        "6" -> "mno",
        "7" -> "pqrs",
        "8" -> "tuv",
        "9" -> "wxyz"
      )

    val output: ListBuffer[String] = ListBuffer.empty

    def backtrack(combination: String, nextDigits: String): Unit = {
      if (nextDigits.length == 0) {
        output.append(combination)
      } else {
        val digit = nextDigits.substring(0, 1)
        val letters = lookUp(digit)
        for (i <- 0 until letters.length) {
          val letter = letters.substring(i, i + 1)
          backtrack(combination + letter, nextDigits.substring(1))
        }
      }
    }
    if (digits.length != 0)
      backtrack("", digits)
    output.toList
  }

  def main(args: Array[String]): Unit = {
    println(officialSolution("23"))
  }
}
