/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 415. 字符串相加(Easy)
  * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
  * 注意：
  * 不能使用任何內建`BigInteger`库，也不能直接将输入的字符串转换为整数形式
  */
object Leetcode_415 extends App {
  def addStrings(num1: String, num2: String): String = {
    var i = num1.length - 1
    var j = num2.length - 1
    var flag = 0
    val s = new StringBuilder()
    while (i >= 0 || j >= 0) {
      var a = 0
      var b = 0
      if (i >= 0) {
        a = num1(i) - '0'
        i -= 1
      }
      if (j >= 0) {
        b = num2(j) - '0'
        j -= 1
      }
      s.append((a + b + flag) % 10)
      flag = (a + b + flag) / 10
    }
    if (flag > 0) {
      s.append(flag)
    }
    s.reverse.toString()
  }
  assert(addStrings("1", "9") == "10")
  assert(addStrings("6994", "36") == "7030")
}
