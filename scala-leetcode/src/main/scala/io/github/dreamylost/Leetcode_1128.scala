/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 1128. 等价多米诺骨牌对的数量
 * 形式上，dominoes[i] = [a, b] 和 dominoes[j] = [c, d] 等价的前提是 a==c 且 b==d，或是 a==d 且 b==c。
 * 输入：dominoes = [[1,2],[2,1],[3,4],[5,6]]
 * 输出：1
 *
 * @author 梦境迷离
 * @version 1.0,2021/1/26
 */
object Leetcode_1128 extends App {
  def numEquivDominoPairs(dominoes: Array[Array[Int]]): Int = {
    import scala.collection.mutable
    case class Dominoes(x: Int, y: Int)
    val map = mutable.HashMap[Dominoes, Int]()
    val ds = dominoes.map(f => if (f(0) > f(1)) Dominoes(f(0), f(1)) else Dominoes(f(1), f(0)))
    ds.foreach(f => map.put(f, map.getOrElse(f, 0) + 1))
    var res = 0
    for (i <- map.values) {
      res = res + (if (i > 1) i * (i - 1) / 2 else 0)
    }
    res
  }

  val case1 = Array(Array(1, 2), Array(1, 2), Array(1, 1), Array(1, 2), Array(2, 2))
  val case2 = Array(Array(1, 2), Array(2, 1), Array(3, 4), Array(5, 6))
  val ret1 = numEquivDominoPairs(case1)
  val ret2 = numEquivDominoPairs(case2)
  println(ret1)
  println(ret2)
}
