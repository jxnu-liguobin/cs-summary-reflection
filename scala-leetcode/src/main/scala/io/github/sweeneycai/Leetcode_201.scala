/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 201. 数字范围按位与 (Medium)
 * 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。
 */
object Leetcode_201 extends App {
  def rangeBitwiseAnd(m: Int, n: Int): Int = {
    // 问题的关键在于求出二进制的公共前缀
    var b = n
    while (m < b) {
      // 和比自身小 1 的数相与，可以消去二进制表示中最右边的 1
      b = b & (b - 1)
    }
    b
  }
  println(rangeBitwiseAnd(5, 7))
}
