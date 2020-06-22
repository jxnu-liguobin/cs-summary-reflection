package io.github.dreamylost

/**
  * 240. Search a 2D Matrix II (Medium)
  *
  * [
  * [ 1,  5,  9],
  * [10, 11, 13],
  * [12, 13, 15]
  * ]
  *
  * @author 梦境迷离
  * @time 2018年7月17日
  * @version v1.0
  */
object Leetcode_240_Array {

  def main(args: Array[String]) {
    val nmatrix = Array(Array(1, 5, 9), Array(10, 11, 13), Array(12, 13, 15))
    val target = 16
    val ret = Leetcode_240_Array.searchMatrix(nmatrix, target)
    print(ret)
  }

  /**
    * Scala创建二维数组
    *
    * var index = new Array[ArrayBuffer[Int]](10)
    * for(i <- 0 until index.length){
    * index(i) = new ArrayBuffer[Int]()
    * }
    */
  def searchMatrix(matrix: Array[Array[Int]], target: Int): Boolean = {
    if (matrix == null || matrix.length == 0 || matrix(0).length == 0)
      return false
    val m = matrix.length
    val n = matrix(0).length
    var row = 0
    var col = n - 1
    while (row < m && col >= 0) {
      if (target == matrix(row)(col))
        return true
      else if (target < matrix(row)(col))
        col = col - 1
      else row = row + 1
    }
    false
  }

}
