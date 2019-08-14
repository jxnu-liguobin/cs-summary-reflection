package cn.edu.jxnu.leetcode.scala

/**
  * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
  *
  * @author 梦境迷离
  * @time 2018-09-05
  */
object MinimumPathSum extends App {

  val ret = minPathSum(Array(Array(1, 3, 1), Array(1, 5, 1), Array(4, 2, 1)))
  println(ret)

  def minPathSum(grid: Array[Array[Int]]): Int = {

    if (grid == null || grid.length == 0 || grid(0).length == 0) {
      return 0
    }

    val M = grid.length
    val N = grid(0).length
    val sum = Array.ofDim[Int](M, N)
    sum(0)(0) = grid(0)(0)
    //对行进行求和
    for (i <- 1 until M) {
      sum(i)(0) = sum(i - 1)(0) + grid(i)(0)
    }
    //对列进行求和
    for (i <- 1 until N) {
      sum(0)(i) = sum(0)(i - 1) + grid(0)(i)
    }
    //直接用已有的2维数组 一步步叠加后，寻找最小
    //每次只能向下或者向右移动一步。
    for (i <- 1 until M) {
      for (j <- 1 until N) {
        sum(i)(j) = math.min(sum(i - 1)(j), sum(i)(j - 1)) + grid(i)(j)
      }
    }
    return sum(M - 1)(N - 1)
  }
}
