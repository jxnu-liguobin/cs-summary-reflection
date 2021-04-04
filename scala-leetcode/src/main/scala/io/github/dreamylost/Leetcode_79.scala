/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 79. 单词搜索
 *
 * @author 梦境迷离
 * @since 2021/4/4
 * @version 1.0
 */
object Leetcode_79 extends App {

  object Solution {

    /**
     * 708 ms,16.67%
     * 50.a8 MB,83.33%
     */
    def exist(board: Array[Array[Char]], word: String): Boolean = {
      //深度优先搜索的方向
      val directions = Seq((0, 1), (0, -1), (1, 0), (-1, 0))
      //记录被访问的节点
      val visited =
        Array[Array[Boolean]](board.indices.map(_ => new Array[Boolean](board(0).length)): _*)

      def inArea(x: Int, y: Int) = x >= 0 && x < board.length && y >= 0 && y < board(0).length

      def dfs(x: Int, y: Int, begin: Int): Boolean = {
        if (begin == word.length - 1) return board(x)(y) == word(begin)
        if (!inArea(x, y)) return false
        if (board(x)(y) == word(begin)) {
          visited(x)(y) = true //设置标记
          for (direction <- directions) {
            val newX = x + direction._1
            val newY = y + direction._2
            if (inArea(newX, newY) && !visited(newX)(newY) && dfs(newX, newY, begin + 1)) {
              return true
            }
          }
          visited(x)(y) = false //回溯恢复标记

        }
        false
      }

      for (i <- board.indices) {
        for (j <- board(0).indices) {
          if (board(i)(j) == word(0) && dfs(i, j, 0)) {
            return true
          }
        }
      }
      false
    }
  }

  val a = Array(
    Array(
      'A',
      'B',
      'C',
      'E'
    ),
    Array(
      'S',
      'F',
      'C',
      'S'
    ),
    Array(
      'A',
      'D',
      'E',
      'E'
    )
  )

  val res = Solution.exist(a, "ABCCED")
  println(res)

}
