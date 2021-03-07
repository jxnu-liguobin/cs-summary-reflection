/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 119. 杨辉三角 II
  * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
  * @author 梦境迷离
  * @since 2021/3/7
  * @version 1.0
  */
object Leetcode_119 extends App {

  //468 ms,84.62%
  //48.8 MB,100.00%
  def getRow(rowIndex: Int): List[Int] = {
    val res: Array[Int] = new Array[Int](rowIndex + 1)
    res(0) = 1
    for (i <- 1 to rowIndex) {
      for (j <- (1 to i).reverse) {
        res(j) = res(j) + res(j - 1)
      }
    }
    res.toList
  }

  println(getRow(3))

  //508 ms,11.54%
  //49.4 MB,50.00%
  def getRow_(rowIndex: Int): List[Int] = {
    var rowList: LazyList[Int] = LazyList[Int]()
    rowList = rowList.appended(1)
    if (rowIndex == 0) return rowList.toList
    val lastRow: List[Int] = getRow_(rowIndex - 1)
    var i = 0
    while (i < lastRow.size - 1) {
      rowList = rowList.appended(lastRow(i) + lastRow(i + 1))
      i += 1
    }
    rowList = rowList.appended(1)
    rowList.toList
  }

  println(getRow_(3))

}
