/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.examples.reflect

/**
  * @author 梦境迷离
  * @since 2020-03-07
  * @version v1.0
  */
class ReflectDemoTestClass {

  def testMethod(name: String) = {
    println("println in testMethod = " + name)
    name
  }

}

object ReflectDemoTestClass {
  //模拟Java的静态方法
  def testStaticMethod(name: String) = {
    println("println in testMethod = " + name)
    name
  }

}
