/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

class ObjectBasic {

  private val str1 = "hello"

  def print(): Unit = {
    println(ObjectBasic.str2)
  }

  // 源文件名称可以和类名不同
}

/**
  * ============================构造函数、实例化规则==============================================
  */
//伴生对象与伴生类同名，同一源文件中
//注意单例对象是一等的，是特殊的class
object ObjectBasic extends App {
  //启动Scala程序
  //1.混入App，默认就可以执行里面的语句
  //2.增加自己的main方法
  //3.混入特质并重写main
  //继承/混入APP特质
  //单例可以混入特质
  private val str2 = "world"
  val companionClass = new ObjectBasic() //new只能实例化类
  println(companionClass.str1) //单例对象类似Java的static方法调用
  companionClass.print() //可以互相访问对方的私有属性，方法

}
