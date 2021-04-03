/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 696. 计数二进制子串 (Easy)
 * 给定一个字符串s，计算具有相同数量0和1的非空(连续)子字符串的数量，
 * 并且这些子字符串中的所有0和所有1都是组合在一起的。
 *
 * 重复出现的子串要计算它们出现的次数。
 */
object Leetcode_696 extends App {
  def countBinarySubstrings(s: String): Int = {
    if (s.length == 0) {
      return 1
    }
    // 使用`foldLeft`对输入的字符串进行分割，将0和1区分开。
    val splitUsingFold = s.foldLeft(Array[String]()) { (string, char) =>
      if (string.isEmpty) string :+ char.toString
      else {
        if (string(string.length - 1).charAt(0) == char) {
          string(string.length - 1) += char
          string
        } else {
          string :+ char.toString
        }
      }
    }
    var res = 0
    for (i <- splitUsingFold.indices.drop(1)) {
      res +=
        math.min(splitUsingFold(i).length, splitUsingFold(i - 1).length)
    }
    res
  }

  println(countBinarySubstrings("1010100111100111"))
}
