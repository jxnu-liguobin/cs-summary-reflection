/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable

/**
  * 39. 组合总和 (Medium)
  *
  * 给定一个无重复元素的数组candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
  *
  * candidates中的数字可以无限制重复被选取。
  */
object Leetcode_39 extends App {
  // 暴力解法, 740ms
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    val res: mutable.Set[List[Int]] = mutable.Set.empty

    def recur(path: List[Int], sum: Int): Unit = {
      if (sum > target) return
      if (sum == target) res.add(path.sorted)
      for (i <- candidates) {
        recur(i :: path, sum + i)
      }
    }

    recur(List.empty[Int], 0)
    res.toList
  }

  /**
    * 620ms，原题解利用了回溯解法，得益于Scala不可变特性，我们使用剪枝即可
    *
    * @see <a href="https://leetcode-cn.com/problems/combination-sum/solution/hui-su-suan-fa-jian-zhi-python-dai-ma-java-dai-m-2/">leetcode-cn.com</a>
    */
  def backdrop(candidates: Array[Int], target: Int): List[List[Int]] = {
    val res: ListBuffer[List[Int]] = ListBuffer.empty
    val sorted = candidates.sorted // 先对候选数字排序，方便剪枝

    /**
      * 深度优先搜索过程
      *
      * @param begin 开始搜索的位置
      * @param len   要搜索的长度
      * @param path  记录之前访问过的路径
      * @param sum   当前路径的和
      */
    def dfs(begin: Int, len: Int, path: List[Int], sum: Int): Unit = {
      if (sum == target) {
        res.append(path)
        return
      }
      breakable {
        for (i <- begin until len) {
          // 超过目标值就剪枝
          if (sum + sorted(i) > target)
            break
          // 下一次搜索时，要将之前搜索过的变量剪枝，即
          // begin = i
          dfs(i, len, sorted(i) :: path, sum + sorted(i))
        }
      }
    }

    if (candidates.length == 0) List.empty[List[Int]]
    else {
      dfs(0, sorted.length, List.empty[Int], 0)
      res.toList
    }
  }

  println(combinationSum(Array(2, 3, 6, 7), 8))
  println(backdrop(Array(2, 3, 6, 7), 7))
}
