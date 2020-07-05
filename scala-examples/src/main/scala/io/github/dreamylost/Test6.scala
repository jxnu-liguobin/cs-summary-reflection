/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object Test6 extends App {

  import scala.collection.mutable.ListBuffer

  //List是具体实现，函数式不可变集合，相当于Java的LinkedList，但是Java没有不可变对象，它们不完全相同。Sequence相当于Java的List
  val list = List(1, 2, 3) //不需要new，使用函数风格的调用，底层调用了List的伴生对象的工厂方法List.apply()
  //val list = 1::2::3::4::Nil 更麻烦的初始化方法，必须用Nil，因为4是整形没有::方法
  list.foreach(print)
  println()

  val list1 = List(1, 2, 3)
  println("列表拼接")
  val list2 = list ::: list1
  list2.foreach(print)
  println()

  println("向列表头部追加元素")
  val list3 = 2 :: list //以冒号结尾的操作符，方法调用发生在右边，其他的都发生在左边
  //list3.:+(2)//效率低，每次都需要从头部移动到尾部，可以使用ListBuffer可变列表或者使用头部追加再反转列表
  list3.foreach(print)
  println()

  println("=================列表操作================")
  val list4 = List("hello", "world", "12344")
  val ret = list4.count(s => s.length == 5)
  println("列表中字符串长度为5的个数：" + ret)

  println("按照首字母排序")
  //不改变原列表
  val list5 = ListBuffer("hello", "world", "hello")
  val list6 = list5.sortWith((s, t) => s.charAt(0).toUpper < t.charAt(0).toUpper) //与元组不同，List从0开始
  list6.foreach(print)
  println()
}
