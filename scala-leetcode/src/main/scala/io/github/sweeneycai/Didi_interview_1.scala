/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 给定一个数 n ，返回一个 n * n 的矩阵。
  * 要求该矩阵从中心开始，逆时针填充斐波那契数列。
  *
  * 如： n = 3
  * 返回矩阵：
  * [34, 21, 13]
  * [1, 1, 8]
  * [2, 3, 5]
  */
object Didi_interview_1 extends App {
  def genFibs(n: Int): Array[Int] = {
    val array: Array[Int] = new Array[Int](n)
    var a = 1
    var b = 1
    array(0) = a
    for (i <- 1 until n) {
      val tmp = a + b
      a = b
      b = tmp
      array(i) = a
    }
    array.reverse
  }

  // 参考剑指Offer，顺时针打印矩阵
  def genMatrix(n: Int): Array[Array[Int]] = {
    val fibs: Array[Int] = genFibs(n * n)
    val res: Array[Array[Int]] = Array.ofDim(n, n)
    var cur = 0
    var up = 0
    var down = n - 1
    var left = 0
    var right = n - 1
    while (left <= right && up <= down) {
      for (i <- left to right) {
        if (cur < fibs.length) {
          res(up)(i) = fibs(cur)
          cur += 1
        }
      }
      for (i <- up + 1 to down) {
        if (cur < fibs.length) {
          res(i)(right) = fibs(cur)
          cur += 1
        }
      }
      for (i <- right - 1 to left by -1) {
        if (cur < fibs.length) {
          res(down)(i) = fibs(cur)
          cur += 1
        }
      }
      for (i <- down - 1 to left + 1 by -1) {
        if (cur < fibs.length) {
          res(i)(left) = fibs(cur)
          cur += 1
        }
      }
      left += 1
      right -= 1
      up += 1
      down -= 1
    }
    res
  }

  genMatrix(3).foreach(i => println(i.mkString("Array(", ", ", ")")))
}
