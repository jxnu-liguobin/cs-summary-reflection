/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.`macro`

/**
 * @author liguobin@growingio.com
 * @version 1.0,2021/1/7
 */
object Macros2 extends App {

  import scala.language.experimental.macros
  import scala.reflect.macros.whitebox

  object MacrosWhitebox {
    def hello: Unit = macro helloImpl

    def helloImpl(c: whitebox.Context): c.Tree = {
      import c.universe._
      q"""println("hello!")"""
    }

    import scala.language.experimental.macros
    import scala.reflect.macros.blackbox

    object HelloQ {
      def hello(msg: String): Unit = macro helloImpl

      def helloImpl(c: blackbox.Context)(msg: c.Expr[String]): c.Expr[Unit] = {
        import c.universe._
        c.Expr(q"""println("hello!")""")
      }
    }

  }

  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox

  object MacrosBlackbox {

    def hello: Unit = macro helloImpl

    def helloImpl(c: blackbox.Context): c.Expr[Unit] = {
      import c.universe._
      c.Expr {
        Apply(
          Ident(TermName("println")),
          List(Literal(Constant("hello!")))
        )
      }
    }
  }

}
