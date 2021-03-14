/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.`macro`

import io.github.dreamylost.`macro`.ImplicitMacros.show
import io.github.dreamylost.`macro`.ImplicitMacros.Showable

/**
  * Scala 隐式宏
  *
  * @see https://docs.scala-lang.org/overviews/macros/implicits.html
  * @author 梦境迷离
  * @version 1.0,2020/3/17
  */
object ImplicitMacros {

  ///类 类型
  trait Showable[T] {
    def show(x: T): String
  }

  def show[T](x: T)(implicit s: Showable[T]) = s.show(x)

  //限定show的参数只能是整数
  implicit object IntShowable extends Showable[Int] {
    def show(x: Int) = x.toString
  }

  show(42) // "42"，使用了隐式类IntShowable
  //show("42") // 编译错误
}

object ImplicitMacros2 {

  ///多个不同类需要打印功能，可能出现模板代码
  class C(val x: Int)

  implicit def cShowable =
    new Showable[C] {
      def show(c: C) = "C(" + c.x + ")"
    }

  class D(val x: Int)

  implicit def dShowable =
    new Showable[D] {
      def show(d: D) = "D(" + d.x + ")"
    }

  //上面模板代码可以使用隐式宏，既解决冗余代码又不影响性能
  object Showable {

    //伴生对象，也就是在trait Showable的隐式范围内，若没有提供显示Showable，则将使用此隐式方法
    //隐式宏可以很好的与普通的隐式结构相融合
    //    implicit def materializeShowable[T]: Showable[T] = macro ???

  }

  implicit def listShowable[T](implicit s: Showable[T]) =
    new Showable[List[T]] {
      def show(x: List[T]) = x.map(s.show).mkString("List(", ", ", ")")
    }

  show(List(42))
}
