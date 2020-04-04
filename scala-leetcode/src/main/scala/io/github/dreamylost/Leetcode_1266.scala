package io.github.dreamylost

/**
 * 平面上有 n 个点，点的位置用整数坐标表示 points[i] = [xi, yi]。请你计算访问所有这些点需要的最小时间（以秒为单位）。
 * 切比雪夫距离
 *
 * @author 梦境迷离
 * @since 2020-01-09
 * @version v1.0
 */
object Leetcode_1266 extends App {

  println(minTimeToVisitAllPoints(Array(Array(1, 1), Array(3, 4), Array(-1, 0))))

  //[[1,1],[3,4],[-1,0]],先对所有点提取同轴坐标，两两计算同轴再求abs和max、sum
  def minTimeToVisitAllPoints(points: Array[Array[Int]]): Int = {
    points.zipWithIndex.collect { //为了函数式而函数式(滑稽)
      case (num, index) if index < points.length - 1 =>
        Array(Math.max(Math.abs(num(0) - points(index + 1)(0)), Math.abs(num(1) - points(index + 1)(1))))
    }.flatten.sum
  }
}
