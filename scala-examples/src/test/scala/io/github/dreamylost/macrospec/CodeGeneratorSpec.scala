/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.macrospec

import io.github.dreamylost.`macro`.CodeGenerator.TemplateArgs
import io.github.dreamylost.`macro`.CodeGenerateBuilder
import io.github.dreamylost.`macro`.CodeGenerator

import scala.reflect.runtime.universe.reify

/**
 * @author 梦境迷离
 * @since 2021/4/8
 * @version 1.0
 */
object CodeGeneratorSpec extends App {

  val codeGenerator = CodeGenerateBuilder[CodeGenerator](
    "GeneratorName",
    "template/name.ftl",
    "classSuffix",
    "codePath"
  )
  println(codeGenerator.classSuffix == "classSuffix") //true
  println(codeGenerator.codePath == "codePath") //true
  println(codeGenerator.templateName == "template/name.ftl") //true
  println(codeGenerator.getClass.getCanonicalName) //null, 因为是匿名类
  println(codeGenerator.getClass.getSimpleName) //u0022GeneratorName$u0022$1
  println(
    codeGenerator.getClass.getInterfaces.map(_.getName).toSeq
  ) //ArraySeq(io.github.dreamylost.macro.CodeGenerator)
  println(codeGenerator.explain(TemplateArgs.apply("event"))) //SourceResult(hello,world)

  //获取ast
  println(
    reify(codeGenerator)
  ) //Expr[io.github.dreamylost.macro.CodeGenerator](CodeGeneratorSpec.this.codeGenerator)
  println(
    reify(codeGenerator.getClass)
  ) //Expr[Class[_ <: io.github.dreamylost.macro.CodeGenerator]](CodeGeneratorSpec.this.codeGenerator.getClass())

  //编译错误
//  val codeGeneratorObjectType = CodeGenerateBuilder[CodeGenerator]("GeneratorName", "template/name.ftl", "classSuffix", "codePath", "")
//  println(codeGeneratorObjectType)

}
