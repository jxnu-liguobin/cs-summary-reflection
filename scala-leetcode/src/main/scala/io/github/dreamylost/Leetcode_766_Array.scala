/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 对角元素相等的矩阵（托普利茨矩阵）
  *
  * 766. Toeplitz Matrix (Easy)
  *
  * 1234
  * 5123
  * 9512
  *
  * In the above grid, the diagonals are "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]",
  * and in each diagonal all elements are the same, so the answer is True.
  * @author 梦境迷离
  * @time 2018年7月23日
  * @version v1.0
  */
object Leetcode_766_Array extends App {

  val n1 = Array(1, 2, 3, 4)
  val n2 = Array(5, 1, 2, 3)
  val n3 = Array(9, 5, 1, 2)
  val nums = Array(n1, n2, n3)
  print(isToeplitzMatrix(nums))

  /**
    * 652 ms,100.00%
    * 52.4 MB,100.00%
    *
    * @param matrix
    * @return
    */
  def isToeplitzMatrix(matrix: Array[Array[Int]]): Boolean = {
    for (i <- matrix(0).indices) {
      if (!check(matrix, matrix(0)(i), 0, i)) {
        return false
      }
    }

    for (i <- matrix.indices) {
      if (!check(matrix, matrix(i)(0), i, 0)) {
        return false
      }
    }

    true

  }
  def check(matrix: Array[Array[Int]], expectValue: Int, row: Int, col: Int): Boolean = {
    if (row >= matrix.length || col >= matrix(0).length) {
      return true
    }
    if (matrix(row)(col) != expectValue) {
      return false
    }
    check(matrix, expectValue, row + 1, col + 1)
  }

}
