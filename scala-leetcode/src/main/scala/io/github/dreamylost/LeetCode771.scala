package io.github.dreamylost

/**
 * 求S中符合J的元素的个数
 * 简单粗暴
 *
 * @author 梦境迷离
 * @since 2020-01-01
 * @version v1.0
 */
object LeetCode771 extends App {

  println(numJewelsInStones("aA", "aAAbbbb"))

  def numJewelsInStones(J: String, S: String): Int = {
    S.toSeq.count(J.toSeq.contains)
  }
}
