package io.github.dreamylost.examples.reflect

/**
  * 不推荐使用
  *
 * 若ReflectDemoTestClass是Java类，可以
  *
 * @author 梦境迷离
  * @since 2020-03-07
  * @version v1.0
  */
object JavaReflectDemoTest extends App {

  def method(): Unit = {
    //反射调用对象方法，虽然是以Scala编写，但是使用的是Java的反射API（对于一些Scala的类型会获取类型失败）
    val clazz = Class.forName("cn.edu.jxnu.scala.reflect.ReflectDemoTestClass")
    val ret = clazz
      .getDeclaredMethod("testMethod", classOf[String])
      .invoke(clazz.newInstance(), "test name for args")
      .asInstanceOf[String]
    println(ret)
  }

  def staticMethod(): Unit = {
    //模拟Java反射调用静态方法，obj=null
    val clazz2 = Class.forName("cn.edu.jxnu.scala.reflect.ReflectDemoTestClass")
    val ret2 = clazz2
      .getDeclaredMethod("testStaticMethod", classOf[String])
      .invoke(null, "test name for args")
      .asInstanceOf[String]
    println(ret2)
  }
}
