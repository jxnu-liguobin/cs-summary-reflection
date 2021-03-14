/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * @author 梦境迷离
  * @version 1.0,2020/3/13
  */
object OutPrivateClass extends App {

  private val name: String = "hello"

  //    def getInnerName = println(InnerPrivateClass.innerName) //编译出差，禁止访问内层单例的私有

  object InnerPrivateClass {

    private val innerName: String = "hello"

    def test() = print(name)
  }

  InnerPrivateClass.test() //内层单例，可以读取外层单例的私有属性

}

class OutPrivateClass {
  //外层类别名，这之间不能有任何代码
  outer =>

  class InnerPrivateClass2 {
    //访问内层类的私有，拒绝访问
    // private val innerName = "world"//
    //可以访问
    val innerName = "world"

    def info() = println("访问外层类的私有属性试试：" + name)

    // 在内层类通过【外层类.this.成员名称】 访问外层类成员
    def info1 = println("Outer name :" + OutPrivateClass.this.name + ",Inner Name :" + name)

    //在内层类通过【外层类别名】 访问外层类成员
    def info2 = println("Outer name :" + outer.name + ",Inner Name :" + name)

  }

  // 访问内层类的私有，拒绝访问（即使你new了这个对象，你也无法得到私有属性，是非常严格的判定，与Java不同）
  // def getInnerName = new InnerPrivateClass2().innerName
  //可以访问
  def getInnerName = println("外层类访问内层类的属性：" + new InnerPrivateClass2().innerName)

  // private val name: String = "hello" //下面方法均正常输出
  // val name: String = "hello" //下面方法均正常输出
  //限定具体的包的权限
  private[dreamylost] val name: String = "hello" //下面方法均正常输出

}

object TestInnerPrivateClass extends App {

  val out1 = new OutPrivateClass()
  out1.getInnerName
  val inner1 = new out1.InnerPrivateClass2() //注意：Scala内层类是从属于外层类对象的。类似Java的static内部类的实例方式
  inner1.info()
  inner1.info1
  inner1.info2 //定义的时候不加括号，调用的时候就不能加

  //PS:内部==内层==被嵌套类，外部==外层==嵌套类
}
