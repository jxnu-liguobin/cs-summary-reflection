/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object Test7 extends App {
  val tuple1 = (11, "hello") //可见，类型可以不同。同样元组也是不可变
  print(tuple1._1) //打印第一个，从1开始，跟随Haskell、ML等静态类型元组设定的语言传统
  print(tuple1._2) //打印第一个，标准库最大22个元组(从概念上可以创建任意长度)
  // print(tuple1(1))//不能使用下标，因为apply是只能返回同种类型
}
