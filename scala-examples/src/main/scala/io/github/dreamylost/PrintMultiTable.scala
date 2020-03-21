package io.github.dreamylost

/**
 * P138 例子
 *
 * 函数式输出乘法表
 */
object PrintMultiTable extends App {

  val ret = multiTable()

  println {
    ret
  }

  def makeRowSeq(row: Int) =
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }

  def makeRow(row: Int) = makeRowSeq(row).mkString

  def multiTable() = {
    val tableSqe = for (row <- 1 to 10) yield makeRow(row)

    tableSqe.mkString("\n")
  }

}