/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object HashEquals extends App {

  val intVal = new Integer(1)
  val longVal = new java.lang.Long(2) //不加包名的话，默认导入scala.Long

  println(intVal == longVal) //输出true，Scala中==等同于equals

  println(intVal.##) //scala hashcode
  println(longVal.##)

  println(intVal eq (longVal)) //false,比较引用的相等性，效果等同于Java的引用比较（Ref1==Ref2）
  println(intVal ne (longVal)) //true,引用不等
}
