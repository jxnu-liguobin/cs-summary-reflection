/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

//待补
object Singleton {
  def main(args: Array[String]) = {
    SingletonObject.hello()
  }
}

/**
 * 每个生成两个class文件
 *
 * Singleton$.class
 *
 * Singleton.class 类似java的class,scala的class也是通过*.class跳转进入*$.class
 *
 * SingletonObject$.class
 *
 * SingletonObject.class
 */
//函数的执行体主要是在 Singleton$.class 中，当执行Singleton.class 中 的main 方法时，会调用方法 Singleton.main(null),
// 接着会执行方法体中的代码 Singleton..MODULE$.main(paramArrayOfString) ，
// 接着函数跳转到Singleton$.class 的 main 方法中，然后执行 Predef..MODULE$.hello()。
//---------------------
object SingletonObject { //object就是单例的
  def hello() = {
    println("Hello, This is a Singleton Object")
  }
}
