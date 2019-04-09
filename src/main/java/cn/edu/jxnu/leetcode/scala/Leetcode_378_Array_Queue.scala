package cn.edu.jxnu.leetcode.scala

import java.util.{PriorityQueue => _}//隐藏java集合

import scala.collection.mutable.PriorityQueue

/**
 * 使用scala队列实现378
 */
object Leetcode_378_Array_Queue extends App {

    class Tuple(var x: Int, var y: Int, var v: Int) extends Comparable[Tuple] {

        override def compareTo(that: Tuple): Int = {
            return this.v - that.v
        }
    }

    /**
     * 无法识别运行main的时候记得重新编译一下
     */
    override def main(args: Array[String]) {

        val nums = Array(Array(1, 5, 9), Array(10, 11, 13), Array(12, 13, 15))
        val k = 8
        val ret = kthSmallest(nums, k)
        print(ret)

    }

    def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
        val m = matrix.length
        val n = matrix(0).length
        val pq = new PriorityQueue[Tuple]
        for (j ← 0 until n) {
            pq.enqueue(new Tuple(0, j, matrix(0)(j)))
            // pq.offer(new Tuple(0, j, matrix(0)(j))
        }
        for (i ← 0 until k - 1) { // 小根堆，去掉 k - 1 个堆顶元素，此时堆顶元素就是第 k 的数
            val t = pq.dequeue()
            import util.control.Breaks._
            breakable(
                if (t.x == m - 1) break)
            pq.enqueue(new Tuple(t.x + 1, t.y, matrix(t.x + 1)(t.y)))
        }
        return pq.dequeue().v
    }

}

