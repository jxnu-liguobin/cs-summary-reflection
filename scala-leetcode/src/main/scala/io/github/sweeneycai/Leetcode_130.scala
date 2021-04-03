/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 130. 被围绕的区域 (Medium)
 *
 * 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
 *
 * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 */
object Leetcode_130 extends App {

  /**
   * 典型的深度优先搜索题目。可以从矩阵四周查找所有有关联的 'O' ，然后
   * 再修改矩阵内部所有孤立的 'O' 。
   */
  def solve(board: Array[Array[Char]]): Unit = {
    if (board.isEmpty) return
    val L = board.length
    val W = board(0).length
    var set: Set[(Int, Int)] = Set.empty

    def recur(i: Int, j: Int): Unit = {
      if (i < 0 || i >= L || j < 0 || j >= W || set.contains((i, j)) || board(i)(j) != 'O')
        return
      set = set + (i -> j)
      recur(i - 1, j)
      recur(i + 1, j)
      recur(i, j - 1)
      recur(i, j + 1)
    }

    for (i <- 0 until L) {
      if (board(i)(0) == 'O') recur(i, 0)
      if (board(i)(W - 1) == 'O') recur(i, W - 1)
    }

    for (j <- 0 until W) {
      if (board(0)(j) == 'O') recur(0, j)
      if (board(L - 1)(j) == 'O') recur(L - 1, j)
    }

    for (i <- 0 until L; j <- 0 until W) {
      if (board(i)(j) == 'O' && !set.contains((i, j)))
        board(i)(j) = 'X'
    }
  }

  val array = Array(
    Array('X', 'X', 'X', 'X'),
    Array('X', 'O', 'O', 'X'),
    Array('X', 'X', 'O', 'X'),
    Array('X', 'O', 'X', 'X')
  )
  solve(array)
  println(array.map(_.mkString("Array(", ", ", ")")).mkString("Array(\n", "\n", "\n)"))
}
