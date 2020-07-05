/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * @author 梦境迷离
  * @version 1.0, 2019-06-20
  */
object MainObject1 extends App {

  //lazy var 是不允许的，
  //常量，不可被覆盖，不可修改，懒值
  private final lazy val default1 = Seq(1, 2, 3, 4)
  println("final lazy val 初始化 =>>>>> " + default1)

  //变量，可被修改不可被覆盖，输出null
  private final var default2 = Seq(1, 2, 3, 4)
  println("final var 初始化 =>>>>> " + default2)

  //常量，不可变，懒值
  private lazy val default3 = Seq(1, 2, 3, 4)
  println("lazy val 初始化 =>>>>> " + default3)

  //常量，不可变，不可覆盖，输出null
  private final val default4 = Seq(1, 2, 3, 4)
  println("final val 初始化 =>>>>> " + default4)

  //普通常量
  private val default5 = Seq(1, 2, 3, 4)
  println("val 初始化 =>>>>> " + default5)

  //普通变量
  private var default6 = Seq(1, 2, 3, 4)
  println("var 初始化 =>>>>> " + default6)

  def getData() = {
    println("方法调用1 =>>>>>> " + default1)
    println("方法调用2 =>>>>>> " + default2)
    println("方法调用3 =>>>>>> " + default3)
    println("方法调用4 =>>>>>> " + default4)
    println("方法调用5 =>>>>>> " + default5)
    println("方法调用6 =>>>>>> " + default6)
  }

  getData
}
