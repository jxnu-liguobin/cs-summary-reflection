/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
 * 剑指 Offer 41. 数据流中的中位数 (Hard)
 *
 * 如何得到一个数据流中的中位数？
 * 如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 *
 * @see <a href="https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/">leetcode-cn.com</a>
 */
class MedianFinder() {

  /** initialize your data structure here. */
  // 小根堆存储大的一半元素
  val minHeap: mutable.PriorityQueue[Int] =
    mutable.PriorityQueue()(Ordering.Int.reverse)

  // 大根堆存储小的一半元素
  val maxHeap: mutable.PriorityQueue[Int] =
    mutable.PriorityQueue.empty

  def addNum(num: Int) {

    /**
     * 长度相等，我们要向小根堆中添加元素，要添加的元素必须是较大的一半，因此
     * 我们先添加到大根堆中，然后再把大根堆中的最大元素添加到小根堆中。
     *
     * 长度不相等，我们要向大根堆中添加元素，要添加的元素必须是较小的一半，因此我们先将元素
     * 添加至小根堆，再从中取出最小的元素。
     *
     * 这样可以保证大根堆和小根堆的堆顶拥有着数据流中间的两个元素。
     */
    if (minHeap.length == maxHeap.length) {
      maxHeap.enqueue(num)
      minHeap.enqueue(maxHeap.dequeue())
    } else {
      minHeap.enqueue(num)
      maxHeap.enqueue(minHeap.dequeue())
    }
  }

  def findMedian(): Double = {
    if (minHeap.length != maxHeap.length)
      minHeap.head
    else
      (minHeap.head + maxHeap.head).toDouble / 2
  }

}

object Lcof_41 extends App {
  val medianFinder = new MedianFinder
  medianFinder.addNum(1)
  println(medianFinder.findMedian())
  medianFinder.addNum(2)
  println(medianFinder.findMedian())
  medianFinder.addNum(3)
  println(medianFinder.findMedian())
}
