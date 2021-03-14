/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

object Test1 {
  // scala 数组下标用(),泛型用[]
  def main(args: Array[String]) = {
    println(apply(layout, 10))
    useLambda()
  }

  // 函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v
  def apply(f: Int => String, v: Int) = f(v)

  def layout[A](x: A) = "[" + x.toString + "]"

  //声明一个lambda
  //实现对传入的两个参数进行凭借,lambda可以当做def使用
  val lambda = (param1: String, param2: String) => param1 + param2

  def useLambda(): Unit = {
    println(lambda("hello", "world"))
  }
}
