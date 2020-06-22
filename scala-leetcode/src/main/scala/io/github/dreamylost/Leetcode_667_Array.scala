package io.github.dreamylost

/**
  * 数组相邻差值的个数
  *
  * 667. Beautiful Arrangement II (Medium)
  *
  * Input: n = 3, k = 2
  * Output: [1, 3, 2]
  * Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3,
  * and the [2, 1] has exactly 2 distinct integers: 1 and 2.
  * @author 梦境迷离
  * @time 2018年7月19日
  * @version v1.0
  */
object Leetcode_667_Array extends App {

  /**
    * 题目描述：数组元素为 1~n 的整数，要求构建数组，使得相邻元素的差值不相同的个数为 k。
    * 让前 k+1 个元素构建出 k 个不相同的差值，序列为：1 k+1 2 k 3 k-1 ... k/2 k/2+1.
    */
  def constructArray(n: Int, k: Int): Array[Int] = {
    val ret = new Array[Int](n)
    ret(0) = 1
    var interval = k
    for (i <- 1 to k) {
      ret(i) = if (i % 2 == 1) ret(i - 1) + interval else ret(i - 1) - interval
      interval -= 1
    }
    for (i <- k + 1 until n) {
      ret(i) = i + 1
    }
    ret
  }

  val ret = constructArray(3, 2)
  for (i <- ret) {
    print(i)
  }

}
