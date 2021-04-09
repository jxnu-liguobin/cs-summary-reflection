/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.`macro`

import io.github.dreamylost.`macro`.CodeGenerator.SourceResult
import io.github.dreamylost.`macro`.CodeGenerator.TemplateArgs

/**
 * https://dreamylost.cn/scala/Scala-Scala2%E4%B8%AD%E5%AE%8F%E7%9A%84%E5%AE%9E%E9%99%85%E5%BA%94%E7%94%A8.html
 *
 * @author 梦境迷离
 * @since 2021/4/8
 * @version 1.0
 */
trait CodeGenerator {

  val templateName: String
  val classSuffix: String
  val codePath: String

  def generate(args: TemplateArgs): String

  def remove(args: TemplateArgs): String

  def explain(args: TemplateArgs): SourceResult

  def handleSourceResult(args: TemplateArgs): SourceResult = {
    //没有真的实现，仅为了编译，我们只验证宏的功能，具体模板逻辑都去掉了
    SourceResult.apply("hello", "world")
  }

}

object CodeGenerator {

  //模板所需的参数
  case class TemplateArgs(name: String)

  case class SourceResult(codePath: String, content: String)

}

trait CodeGenerateBuilder {

  def buildGenerator(): CodeGenerator
}

object CodeGenerateBuilder {

  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox
  import scala.reflect.macros.whitebox

  /**
   * @param name        Generator的名称，主要用于根据类名进行后续判断
   * @param template    模板路径
   * @param classSuffix 模板类的自定义后缀
   * @param codePath    生成代码的路径
   * @tparam G
   * @return
   */
  def apply[G <: CodeGenerator](
      name: String,
      template: String,
      classSuffix: String,
      codePath: String
  ): G = macro applyImpl[G]

  //该宏生成一个单例对象，注意：孤独的object不是伴生对象。添加一个参数是为了重载apply。
  def apply[G <: CodeGenerator](
      name: String,
      template: String,
      classSuffix: String,
      codePath: String,
      args: String
  ): G = macro applyImpl2

  //黑盒使用c.Expr[T]可以携带参数类型有助于类型推导，会更好。白盒不需要类型推断
  def applyImpl2(c: whitebox.Context)(
      name: c.Tree,
      template: c.Tree,
      classSuffix: c.Tree,
      codePath: c.Tree,
      args: c.Tree
  ): c.universe.Tree = {
    import c.universe._
    val Literal(Constant(sEname: String)) = name
    val ret =
      q"""
      import io.github.dreamylost.`macro`.CodeGenerator._
      import io.github.dreamylost.`macro`.{ Helper, CodeGenerator}
      
      import java.nio.file.Path

      object ${TermName(sEname)} extends CodeGenerator {
    
        override val templateName = $template
        
        override val classSuffix = $classSuffix
        
        override val codePath = $codePath
      
        override def generate(args: TemplateArgs): String = {
          val result = explain(args)
          Helper.writeSourceResult(result)
        }
      
        override def remove(args: TemplateArgs): String = {
          val result = explain(args)
          Helper.deleteSourceCode(result.codePath)
        }
      
        override def explain(args: TemplateArgs): SourceResult = {
          handleSourceResult(args)
        }
    }
    ${TermName(sEname)}
    """
    ret
  }

  def applyImpl[G: c.WeakTypeTag](c: blackbox.Context)(
      name: c.Expr[String],
      template: c.Expr[String],
      classSuffix: c.Expr[String],
      codePath: c.Expr[String]
  ): c.Expr[G] = {
    import c.universe._
    val className = TypeName(name.tree.toString())
    val ret =
      q"""
      import io.github.dreamylost.`macro`.CodeGenerator._
      import io.github.dreamylost.`macro`.{ Helper, CodeGenerator}
      
      import java.nio.file.Path

      class $className extends CodeGenerator {
    
        override val templateName = $template
        
        override val classSuffix = $classSuffix
        
        override val codePath = $codePath
      
        override def generate(args: TemplateArgs): String = {
          val result = explain(args)
          Helper.writeSourceResult(result)
        }
      
        override def remove(args: TemplateArgs): String = {
          val result = explain(args)
          Helper.deleteSourceCode(result.codePath)
        }
      
        override def explain(args: TemplateArgs): SourceResult = {
          handleSourceResult(args)
        }
    }
      new $className
    """
    c.Expr[G](ret)
  }

}

object Helper {
  //没有真的实现，仅为了编译
  def writeSourceResult(result: SourceResult): String = {
    println(s"Write file: $result")
    result.codePath
  }

  def deleteSourceCode(file: String): String = {
    println(s"Delete file: $file")
    file
  }
}
