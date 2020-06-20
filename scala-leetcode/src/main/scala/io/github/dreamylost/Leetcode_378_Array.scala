package io.github.dreamylost

import scala.collection.mutable

/**
  * 有序矩阵的 Kth Element
  *
 * 378. Kth Smallest Element in a Sorted Matrix ((Medium))
  *
 * matrix = [
  * [ 1,  5,  9],
  * [10, 11, 13],
  * [12, 13, 15]
  * ],
  * k = 8,
  * return 13.
  *
 * @author 梦境迷离
  * @time 2018年7月18日
  * @version v1.0
  */
object Leetcode_378_Array extends App { //自带main方法

  def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
    val m = matrix.length
    val n = matrix(0).length
    var lo = matrix(0)(0)
    var hi = matrix(m - 1)(n - 1)
    while (lo <= hi) {
      val mid = lo + (hi - lo) / 2
      var cnt = 0
      for (i ← 0 until m) {
        for (j ← 0 until n if matrix(i)(j) <= mid) {
          cnt += 1 //scala没++  --
        }
      }
      if (cnt < k) lo = mid + 1
      else hi = mid - 1
    }
    lo
  }

  /**
    * 800 ms,33.33%
    * 61 MB,100.00%
    *
   * @param matrix
    * @param k
    * @return
    */
  def kthSmallest2(matrix: Array[Array[Int]], k: Int): Int = {
    val queue = new mutable.PriorityQueue[Int]()
    matrix.indices.foreach(i =>
      matrix.indices.foreach(j => {
        queue.enqueue(matrix(i)(j))
        if (queue.size > k) queue.dequeue
      })
    )
    queue.head
  }

  val nums = Array(Array(1, 5, 9), Array(10, 11, 13), Array(12, 13, 15))
  val k = 8
  val ret = kthSmallest(nums, k)
  print(ret)

}
