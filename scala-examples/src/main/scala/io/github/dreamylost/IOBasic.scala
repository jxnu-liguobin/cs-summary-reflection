/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import scala.io.Source

/**
  * 从文件读取行，文件写入使用Java IO
  *
  * 从命令行运行不用加包名
  * scala IOBasic.scala IOBasic.scala 后面一个是参数
  */
object IOBasic {

  val file =
    "D:\\git_project\\cs-summary-reflection\\src\\cn\\edu\\jxnu\\scala\\basic\\IOExamples.scala"
  val liness = Source.fromFile(file).getLines().toList

  def main(args: Array[String]): Unit = {
    //        for (line <- Source.fromFile(file).getLines()) {
    //            println(line.length + " " + line)
    //        }

    //格式化打印
    val maxWidth = widthOfLength(longestLine)

    for (line <- liness) {
      val nums = maxWidth - widthOfLength(line)
      val padding = " " * nums
      println(padding + line.length + " | " + line)
    }

  }

  /**
    * 计算字符数
    */
  def widthOfLength(s: String) = s.length.toString.length

  /**
    * 计算最大长度 函数式方法
    */
  val longestLine = liness.reduceLeft((a, b) => if (a.length > b.length) a else b)

  /**
    * 计算最大长度 普通函数
    */
  def longestLine2() {

    var maxWidth = 0
    for (line <- liness) {
      maxWidth = maxWidth.max(widthOfLength(line))
    }
  }

}

/**
  * 从网络读取
  */
object Test10 extends App {
  val webFile = Source.fromURL("http://www.baidu.com")
  webFile.foreach(print)
  webFile.close()
}

/**
  * 从控制台读取
  *
  * Java的Scanner也可以
  */
object Test11 extends App {
  //控制台交互
  print("Please enter your input:")
  //val line = Console.readLine()//过期
  val line = scala.io.StdIn.readLine()
  println("Thanks,you just typed:" + line)
}
