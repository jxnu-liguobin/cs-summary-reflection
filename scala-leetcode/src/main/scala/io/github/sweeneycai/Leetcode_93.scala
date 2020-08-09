/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
  * 93. 复原IP地址 (Medium)
  *
  * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
  * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
  */
object Leetcode_93 {

  /**
    * 简单的深度优先搜索，在 2.13.x 中`Stream`可以使用`LazyList`代替
    * 注意考虑'0'作为开头时的特殊情况
    */
  def restoreIpAddresses(s: String): List[String] = {
    if (s.length > 12 || s.length < 4) List.empty[String]
    else {
      (for {
        i <- from(List(s -> List.empty[String]))
        if i._1 == "" && i._2.length == 4
      } yield i._2).map(_.mkString(".")).toList
    }
  }

  type Path = (String, List[String])

  def generate(s: String): List[(String, String)] = {
    val possible = ListBuffer.empty[(String, String)]
    if (
      s.length >= 3 && (s.charAt(0) == '1' || (s.charAt(0) == '2' && s.substring(0, 3) < "256"))
    ) {
      possible.append(s.splitAt(3))
    }
    if (s.length >= 2 && s.charAt(0) != '0') possible.append(s.splitAt(2))
    if (s.length >= 1) possible.append(s.splitAt(1))
    possible.toList
  }

  def next(s: String, history: List[String]): List[Path] = {
    generate(s)
      .foldLeft(List.empty[Path]) { (acc, ss) =>
        acc :+ (ss._2, history :+ ss._1)
      }
      .filter(_._2.length <= 4)
  }

  def from(paths: List[Path]): Stream[Path] =
    if (paths.isEmpty)
      Stream.empty
    else {
      val more = for {
        path <- paths
        next <- next(path._1, path._2)
      } yield next
      from(more).append(paths)
    }

  def main(args: Array[String]): Unit = {
    println(restoreIpAddresses("25345244"))
  }
}
