/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 96. 不同的二叉搜索树 (Medium)
  *
  * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
  * @see <a href="https://leetcode-cn.com/problems/unique-binary-search-trees/">leetcode-cn.com</a>
  */
object Leetcode_96 extends App {
  def numTrees(n: Int): Int = {
    val array = Array.fill(n + 1)(0)
    array(0) = 1
    array(1) = 1
    for (i <- 2 to n) {
      for (j <- 1 to i) {
        array(i) += array(j - 1) * array(i - j)
      }
    }
    array.last
  }

  println(numTrees(6))
}
