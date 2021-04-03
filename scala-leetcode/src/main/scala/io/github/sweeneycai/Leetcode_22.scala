/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
 * 22. 括号生成 (Medium)
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 */
object Leetcode_22 extends App {

  /**
   * 深度优先搜索
   */
  def generateParenthesis(n: Int): List[String] = {
    val listBuffer: ListBuffer[String] = ListBuffer.empty

    def recur(i: Int, left: Int, right: Int, acc: String): Unit = {
      if (i == n * 2) listBuffer.append(acc)
      else {
        // left < n 的时候，可以添加左括号，如果此时right < left，那么就可以添加右括号
        if (left < n) {
          recur(i + 1, left + 1, right, acc + "(")
          if (left > right) recur(i + 1, left, right + 1, acc + ")")
        }
        // 当左括号不能继续添加，并且右括号还可以继续添加的时候，添加右括号
        if (left == n && right < n) recur(i + 1, left, right + 1, acc + ")")
      }
    }

    recur(0, 0, 0, "")
    listBuffer.toList
  }

  /**
   * 官解的回溯解法
   */
  def backtrackSolution(n: Int): List[String] = {
    val listBuffer: ListBuffer[String] = ListBuffer.empty

    def backtrack(cur: StringBuilder, open: Int, close: Int): Unit = {
      if (cur.length() == n * 2) listBuffer.append(cur.toString())
      else {
        // 先添加左括号，并回溯
        if (open < n) {
          cur.append('(')
          backtrack(cur, open + 1, close)
          cur.deleteCharAt(cur.length() - 1)
        }
        // 再添加右括号，并回溯
        if (close < open) {
          cur.append(')')
          backtrack(cur, open, close + 1)
          cur.deleteCharAt(cur.length() - 1)
        }
      }
    }

    backtrack(new StringBuilder, 0, 0)
    listBuffer.toList
  }

  println(generateParenthesis(5).mkString(" "))
  println(backtrackSolution(5).mkString(" "))
}
