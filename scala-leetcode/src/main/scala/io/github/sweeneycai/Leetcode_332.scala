/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
 * 332. 重新安排行程 (Medium)
 *
 * 给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，
 * 对该行程进行重新规划排序。所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，
 * 所以该行程必须从 JFK 开始。
 *
 * 说明:
 *
 * 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。
 * 例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前。
 * 所有的机场都用三个大写字母表示（机场代码）。
 * 假定所有机票至少存在一种合理的行程。
 *
 * @see <a href="https://leetcode-cn.com/problems/reconstruct-itinerary/">leetcode-cn.com</a>
 */
object Leetcode_332 extends App {

  /**
   * 简单图
   *
   * 死胡同必须是最后一个访问到，不然访问完死胡同其他可以访问的就访问不到了。
   * dfs 递归的时候，票少的会先被记录下来，因此死胡同会先被安排。
   */
  def findItinerary(tickets: List[List[String]]): List[String] = {
    val listBuffer: mutable.ListBuffer[String] = mutable.ListBuffer.empty
    val lookUp: mutable.Map[String, mutable.PriorityQueue[String]] = mutable.Map.empty
    for (list <- tickets)
      if (lookUp.contains(list.head)) lookUp(list.head).enqueue(list.last)
      else {
        lookUp.+=(list.head -> mutable.PriorityQueue(list.last)(Ordering.String.reverse))
      }

    def dfs(curr: String): Unit = {
      while (lookUp.contains(curr) && lookUp(curr).nonEmpty) {
        val tmp = lookUp(curr).dequeue()
        dfs(tmp)
      }
      listBuffer.append(curr)
    }

    dfs("JFK")
    listBuffer.reverse.toList
  }

  println(
    findItinerary(
      List(
        List("JFK", "KUL"),
        List("JFK", "NRT"),
        List("NRT", "JFK")
      )
    )
  )

  println(
    findItinerary(
      List(
        List("MUC", "LHR"),
        List("JFK", "MUC"),
        List("SFO", "SJC"),
        List("LHR", "SFO")
      )
    )
  )

  println(
    findItinerary(
      List(
        List("JFK", "SFO"),
        List("JFK", "ATL"),
        List("SFO", "ATL"),
        List("ATL", "JFK"),
        List("ATL", "SFO")
      )
    )
  )

  println(findItinerary(List()))
}
