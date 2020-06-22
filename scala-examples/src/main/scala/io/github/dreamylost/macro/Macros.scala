package io.github.dreamylost.`macro`

import org.slf4j._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.reflect.macros.whitebox.Context

/**
  * Scala宏编程，优化日志
  */
object Macros {

  implicit class LoggerEx(logger: Logger) {
    def DEBUG1(msg: String): Unit = macro LogMacros.DEBUG1

    def DEBUG2(msg: String, exception: Exception): Unit = macro LogMacros.DEBUG2
  }

  object LogMacros {

    def DEBUG1(c: whitebox.Context)(msg: c.Tree): c.Tree = {
      import c.universe._
      val pre = c.prefix
      q"""
         val x = $pre.logger
         if (x.isDebugEnabled) x.debug($msg)
       """
    }

    def DEBUG2(c: Context)(msg: c.Tree, exception: c.Tree): c.Tree = {
      import c.universe._
      val pre = c.prefix
      q"""
         val x = $pre.logger
         if (x.isDebugEnabled) x.debug( $msg, $exception )
       """
    }
  }

  ///不能在定义宏实现的同一编译运行中使用宏实现
  /**
    * {{{
    *     object TestMacros extends App {
    *
    *     //可以直接在sbt中 set scalacOption := Seq(“-Ymacro-debug-lite”)开启选项，查看生成的代码
    *     class LogTest {
    *
    *       val logger = LoggerFactory.getLogger(getClass)
    *       //隐式支持了
    *       logger.DEBUG1(s"Hello, today is ${new java.util.Date}")
    *       logger.DEBUG2(s"Hello, today is ${new java.util.Date}", new Exception("test"))
    *     }
    *
    *   }
    * }}}
    */

}
