/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 347. 前 K 个高频元素
 *
 * @author 梦境迷离
 * @since 2021-04-11
 * @version v1.0
 */
object Leetcode_347 extends App {

  object Solution {

    import scala.math.Ordering

    /**
     * 780 ms,10.00%
     * 57.5 MB,80.00%
     */
    def topKFrequent(nums: Array[Int], k: Int): Array[Int] = {
      val eCount = nums.foldLeft(Map.empty[Int, Int]) { (m, e) =>
        m ++ (if (m.contains(e)) Map(e -> m(e).+(1)) else Map(e -> 1))
      }

      //根据频次排序，第一个元素代表值，第二个元素代表了该值出现的次数
      implicit val ordering: Ordering[(Int, Int)] = new Ordering[(Int, Int)]() {
        override def compare(x: (Int, Int), y: (Int, Int)): Int = x._2 - y._2
      }
      val miniHeap = new java.util.PriorityQueue[(Int, Int)](ordering)
      for ((value, count) <- eCount) {
        if (miniHeap.size == k) {
          if (miniHeap.peek()._2 < count) {
            miniHeap.poll() //REMOVE HEAD
            miniHeap.offer(value -> count)
          }
        } else {
          miniHeap.offer(value -> count)
        }
      }

      var i = 0
      val res = new Array[Int](k)
      while (i < k && !miniHeap.isEmpty) {
        val e = miniHeap.poll()
        res(i) = e._1
        i += 1
      }
      res
    }

    /**
     * 828 ms,10.00%
     * 58.8 MB,10.00%
     *
     * @param nums
     * @param k
     * @return
     */
    def topKFrequent2(nums: Array[Int], k: Int): Array[Int] = {
      nums
        .foldLeft(Map.empty[Int, Int]) { (m, e) =>
          m ++ (if (m.contains(e)) Map(e -> m(e).+(1)) else Map(e -> 1))
        }
        .toArray
        .sortWith((a, b) => b._2 - a._2 < 0)
        .take(k)
        .map(_._1)
    }
  }

  val res = Solution.topKFrequent(
    Array(3, 2, 3, 1, 2, 4, 5, 5, 6, 7, 7, 8, 2, 3, 1, 1, 1, 10, 11, 5, 6, 2, 4, 7, 8, 5, 6),
    10
  )
  val res2 = Solution.topKFrequent(Array(4, 1, -1, 2, -1, 2, 3), 2)
  println(res.toSeq)
  println(res2.toSeq)

  val res3 = Solution.topKFrequent2(
    Array(3, 2, 3, 1, 2, 4, 5, 5, 6, 7, 7, 8, 2, 3, 1, 1, 1, 10, 11, 5, 6, 2, 4, 7, 8, 5, 6),
    10
  )
  println(res3.toSeq)

}
