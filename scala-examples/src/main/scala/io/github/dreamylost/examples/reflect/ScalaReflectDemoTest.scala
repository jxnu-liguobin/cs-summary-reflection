package io.github.dreamylost.examples.reflect

import scala.reflect.runtime.universe

/**
  * 在Scala类上使用Java反射可能会返回令人惊讶或错误的结果
  *
 * 在Scala中最好使用Scala的反射api
  *
 * @see https://docs.scala-lang.org/overviews/reflection/overview.html
  * @author 梦境迷离
  * @since 2020-03-07
  * @version v1.0
  */
object ScalaReflectDemoTest extends App {

  def method() {
    //使用Scala的反射API对象方法
    val classMirror = universe.runtimeMirror(getClass.getClassLoader)
    val classTest = classMirror.reflect(
      new io.github.dreamylost.examples.reflect.ReflectDemoTestClass
    ) //获取需要反射的类对象
    val methods = universe.typeOf[io.github.dreamylost.examples.reflect.ReflectDemoTestClass]
    val method = methods.decl(universe.TermName("testMethod")).asMethod
    val result = classTest.reflectMethod(method)("test name for args")
    val ret = result.asInstanceOf[String]
    println(ret)
  }

  def staticMethod() {
    //使用Scala的反射API对象的静态方法
    val classMirror = universe.runtimeMirror(getClass.getClassLoader) //获取运行时类镜像
    val classTest = classMirror.staticModule(
      "io.github.dreamylost.examples.reflect.ReflectDemoTestClass"
    ) //获取需要反射object
    val methods = classMirror.reflectModule(classTest) //构造获取方式的对象
    val objMirror = classMirror.reflect(methods.instance) //反射结果赋予对象
    val method =
      methods.symbol.typeSignature.member(universe.TermName("testStaticMethod")).asMethod //反射调用函数
    val result = objMirror.reflectMethod(method)("test name for args") //最后带参数,执行这个反射调用的函数
    val ret2 = result.asInstanceOf[String]
    println(ret2)
  }

}
