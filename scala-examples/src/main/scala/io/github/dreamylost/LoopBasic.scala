/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import scala.util.control.Breaks._

/**
  * Java是指令式风格，Scala是函数式风格。
  * 在Scala中，应该尽量适用循环，而是应用函数的方式来处理。
  * Scala并没有提供break和continue语句来退出循环，那么如果我们又确实要怎么办呢，有如下几个选项：
  * 1. 使用Boolean型的控制变量。
  * 2. 使用嵌套函数，从函数当中return
  * 3. 使用Breaks对象中的break方法(这里的控制权转移是通过抛出和捕获异常完成的，尽量避免使用这套机制)
  * 4. 递归函数重写循环
  * 5. Scala的for是for表达式，更接近函数式
  * 6. 纯函数式for：list.foreach(println) @see loopTest.scala
  */
object LoopBasic {
  //这种是独立对象

  //普通遍历整形数组@see Test4.scala
  def main(args: Array[String]) = {
    val list = List(21, 214, -4352, 65436, 7534)
    val list2 = List(212, 2134, -43522, 651436, 7534)

    println("//差集")
    for (i <- list.diff(list2)) { //--
      print(s"$i ")
    }
    println()

    println("//交集")
    for (i <- list.intersect(list2)) { //&
      print(s"$i ")
    }
    println()

    println("//并集")
    for (i <- list.concat(list2).distinct) {
      //  ++  | distinct去重
      print(s"$i ")
    }
    println()

    println("=======================================")
    loopTest(list)

    breakTest1(list)
    breakTest2(list)
    contniueTest1(list)
    continueTest2(list)
    continueTest3(list)

  }

  def loopTest(list: List[Int]): Unit = {
    println("函数式遍历")
    list.foreach(println)
    println()
    println("================================")
  }

  def breakTest1(list: List[Int]) = {
    println("breakable语句块来实现break操作")
    breakable {
      for (elem <- list)
        if (elem < 0) break() else println(elem)
    }
  }

  def breakTest2(list: List[Int]) = {
    println("增加一个boolean变量作为for循环守卫，while循环同理")
    var foundMinus = false
    for (elem <- list if !foundMinus) {
      if (elem < 0) foundMinus = true else println(elem)
    }
  }

  def contniueTest1(list: List[Int]) = {
    println("breakable语句块来实现continue操作")
    for (elem <- list)
      breakable {
        if (elem < 0) break() else println(elem)
      }
  }

  def continueTest2(list: List[Int]) = {
    println("if else控制来实现continue操作")
    for (elem <- list)
      if (elem < 0) () else println(elem)
  }

  def continueTest3(list: List[Int]) = {
    println("递归函数重写循环")

    def next(i: Int): Unit = {
      if (i >= list.size) ()
      else if (list(i) < 0) next(i + 1)
      else println(list(i))
      next(i + 1)
    }

    next(0)
  }

  //Scala赋值语句返回的不是赋值的那个值而是（）
  var line = ""
  while ({
    line = scala.io.StdIn.readLine()
    line
  } != "") {
    //这样永远是（）！=“”
    println(line)
  }

  //需要在外部赋值初始化，并在while里面进行更新
  //因为while没有返回值，并且使用了var变量，实际上函数式语言并不推荐。可以使用尾递归替代
}
