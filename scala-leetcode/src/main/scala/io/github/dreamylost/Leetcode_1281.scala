/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 *  @author 梦境迷离
 *  @version 1.0,2020/3/12
 */
object Leetcode_1281 extends App {
  def subtractProductAndSum(n: Int): Int = {
    val f = n.toString.toCharArray.map(c => Integer.parseInt(c + ""))
    f.product - f.sum
  }

  Console println subtractProductAndSum(234)
}
