package io.github.dreamylost.scala3

/**
  * 特质参数: https://dotty.epfl.ch/docs/reference/other-new-features/trait-parameters.html
  */
object TraitParams:

  trait Base(val msg: String)
  class A extends Base("Hello")
  class B extends Base("Dotty!")

  // 联合类型只存在于Scala3中，所以不可能意外地用Scala 2编译。
  private def printMessages(msgs: (A | B)*) = println(msgs.map(_.msg).mkString(" "))

  def test(): Unit =
    printMessages(new A, new B)

    // 检查类路径的合理性：如果scala3 jar不存在，则不会运行。
    val x: Int => Int = identity
    x(1)

