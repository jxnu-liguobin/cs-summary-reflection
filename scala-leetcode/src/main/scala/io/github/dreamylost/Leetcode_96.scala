/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import scala.collection.immutable.HashMap

/**
  * 96. 不同的二叉搜索树
  *
  * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-28
  * @version v1.0
  */
object Leetcode_96 extends App {

  val ret = numTrees(3)
  val ret2 = numTrees2(3)
  val ret3 = numTrees3(3)
  val ret4 = numTrees4(3)
  println(ret)
  println(ret2)
  println(ret3)
  println(ret4)

  /**
    * 不能直接使用95的代码，会超时
    * 利用递推公式求解
    *
    * fn=f(1)+f(2)+f(3)+...fn(n)
    * 对任意的i，i的左子树有i-1个节点,i的右子树有n-i个节点
    * f(i)=f(i-1)*f(n-i)
    * 代入到fn中，得f(n) = f(0)*f(n-1)+f(1)f(n-2)+f(2)f(n-3)+...+f(n-1)f(0)
    *
    * 4600 ms,25.00%(直接用公式太慢了)
    * 49.2 MB,100.00%
    *
    * @param n
    * @return
    */
  def numTrees(n: Int): Int = {
    //根据地推公式直接递归
    var ret = 0
    if (n == 1 || n == 0) 1
    else {
      for (i <- 1 to n) {
        ret += numTrees(i - 1) * numTrees(n - i)
      }
      ret
    }
  }

  /**
    * 动态规划
    * 460 ms,50.00%
    * 49 MB,100.00%
    *
    * @param n
    * @return
    */
  def numTrees2(n: Int): Int = {
    if (n == 1 || n == 0) return 1
    val ret = new Array[Int](n + 1)
    ret(0) = 1
    for (i <- 1 to n) {
      for (j <- 1 to i) {
        ret.update(i, ret(j - 1) * ret(i - j) + ret(i))
      }
    }
    ret(n)
  }

  /**
    * 与95类似，二叉搜索树的种类只与个数有关，缓存个数
    * 472 ms,50.00%
    * 49.1 MB,100.00%
    *
    * @param n
    * @return
    */
  def numTrees3(n: Int): Int = {
    var map = HashMap.empty[Int, Int]

    def buildSearchTree(left: Int, right: Int): Int = {
      var sum = 0
      if (map.contains(right - left)) {
        return map(right - left)
      }
      if (left > right) {
        1
      } else {
        (left to right).foreach { i =>
          val lCount = buildSearchTree(left, i - 1)
          val rCount = buildSearchTree(i + 1, right)
          sum += lCount * rCount
        }
        map = map ++ Map(right - left -> sum)
        sum
      }
    }

    if (n < 1) 0 else buildSearchTree(1, n)
  }

  /**
    * 上面的递推公式 = 卡特兰数 https://baike.baidu.com/item/catalan/7605685?fr=aladdin
    *
    * 468 ms,50.00%
    * 48.8 MB,100.00%
    *
    * @param n
    * @return
    */
  def numTrees4(n: Int): Int = {
    var ret = 1L
    for (i <- 0 until n) {
      ret = ret * 2 * (2 * i + 1) / (i + 2)
    }
    ret.toInt
  }
}
