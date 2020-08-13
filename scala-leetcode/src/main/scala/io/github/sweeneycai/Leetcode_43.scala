/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.annotation.tailrec

/**
  * 43. 字符串相乘 (Medium)
  *
  * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
  */
object Leetcode_43 extends App {

  @tailrec
  def add(a: String, b: String): String = {
    if (a.length > b.length) add(b, a)
    else {
      var flag = 0
      var res = ""
      val diff = b.length - a.length
      // 先将 a b 公共部分相加
      for (i <- (a.length - 1) to 0 by -1) {
        val tmp = a.charAt(i) - '0' + b.charAt(i + diff) - '0' + flag
        if (tmp >= 10) flag = 1 else flag = 0
        res = tmp % 10 + res
      }
      // 然后处理剩余的 b
      for (j <- (b.length - a.length - 1) to 0 by -1) {
        val tmp = b.charAt(j) - '0' + flag
        if (tmp >= 10) flag = 1 else flag = 0
        res = tmp % 10 + res
      }
      if (flag == 1) "1" + res
      else res
    }
  }

  /**
    * 一位数与多位数相乘。
    *
    * @param offset 扩展为原来的 offset * 10 倍
    */
  def multiOne(num: Int, string: String, offset: Int): String = {
    if (num == 0) "0"
    else {
      var flag = 0
      var res = ""
      for (i <- (string.length - 1) to 0 by -1) {
        val tmp = (string.charAt(i) - '0') * num + flag
        flag = tmp / 10
        res = tmp % 10 + res
      }
      if (flag != 0) flag + res + "0" * offset
      else res + "0" * offset
    }
  }

  /**
    * 两个字符串相加
    */
  @tailrec
  def multiply(num1: String, num2: String): String = {
    if (num1 == "" || num2 == "") ""
    else if (num1.length > num2.length) multiply(num2, num1)
    else {
      val length = num1.length
      var res = "0"
      for (i <- (num1.length - 1) to 0 by -1) {
        res = add(res, multiOne(num1.charAt(i) - '0', num2, length - i - 1))
      }
      res
    }
  }

  println(multiply("123", "456"), 123 * 456)
}
