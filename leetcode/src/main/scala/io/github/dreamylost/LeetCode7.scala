package io.github.dreamylost

/**
 * 反转数字
 * 求余得尾数，以除得十位
 *
 * @author 梦境迷离
 * @since 2020-01-01
 * @version v1.0
 */
object LeetCode7 extends App {

  Console println reverse(1534236469)

  def reverse(x: Int): Int = {
    var n = x
    var m = 0
    var tmp = 0
    while (n != 0) {
      m = m * 10 + n % 10
      if (m / 10 != tmp) {
        return 0
      }
      tmp = m
      n = n / 10
    }
    m
  }
}
