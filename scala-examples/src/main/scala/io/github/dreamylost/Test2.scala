/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object Test2 extends App {
  // 继承App 默认有main方法，自己也可以重写main。或者不继承App，自己写main方法，签名需一致
  val string: String = "ABCabc"
  val hasUpper = string.exists(_.isUpper) //_.isUpper是函数字面量，其中_占位符(args:type)=> func body
  println(hasUpper)

  var i = 0 //scala不能用i++ ++i --i i--
  i += 1
  println(i)

  val str = "hello" +
    "world"; //+操作符放在末尾，而不是java那样推荐在前面
  Console println str

  println {
    "hello"
  } //只有一个参数的方法调用可以使用花括号

  // string.chars().anyMatch((int ch)-> Character.isUpperCase((char)ch)) //java 8
}
