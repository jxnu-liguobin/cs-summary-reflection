package io.github.dreamylost.`macro`

/**
  * @see https://docs.scala-lang.org/overviews/macros/implicits.html#fundep-materialization
  * @author liguobin@growingio.com
  * @version 1.0,2020/3/17
  */
object Fundeps {

  trait Iso[T, U] {
    def to(t: T): U

    def from(u: U): T
  }

  case class Foo(i: Int, s: String, b: Boolean)

  def conv[C, L](c: C)(implicit iso: Iso[C, L]): L = iso.to(c)

  //  val tp  = conv(Foo(23, "foo", true))//必须要能推断L类型
  //  tp: (Int, String, Boolean)
  //  tp == (23, "foo", true)
}
