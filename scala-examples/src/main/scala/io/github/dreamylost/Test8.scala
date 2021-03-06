/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object Test8 extends App {

  println("================Set集===========")
  //创建并初始化，不可变Set集合，类似数组，也是调用了伴生对象的apply方法
  var set = Set("hello", "world") //默认集合都是不可变的， 其他集：var hashSet = HashSet("hello","world")
  set += "hhh" //实际是“+”方法[set = set + "hhh"]， 创建并返回新集合，无论是可变还是不可变集
  println(set.contains("hello"))
  // 注意：因为对不可变集使用+=实际是调用+=方法set1.+=("hhh")，所以表达式不需要重新赋值，set1可以是val
  // 而对于可变集，+=实际是+调用后再进行赋值，所以不能为val
  //对于只有一个参数的方法调用可以省略 . () ，所以set1.+=("hhh") ===> set1 += "hhh"
  val set1 = collection.mutable.Set("hello", "world") //提供+=方法
  set1 += "hhh" //实际是调用方法
  println(set1.contains("hhh"))
  println("===============Set遍历=============")
  set1.foreach(print) //遍历
  println()
  println("================Map集===========")
  //创建并初始化可变的Map
  val map = collection.mutable.Map[String, String]()
  map += ("a" -> "b") //实际是(a).->("b")方法调用，同样也是省略了. ()
  map += ("b" -> "c") //底层也是+=的方法调用，所以map也可以是val
  map += ("c" -> "d") //Map("hello"->"world","a"->"b")默认是不可变，不再解释
  map.foreach { case (k, v) => println(k + v) } //用小括号报错，，，
  println(map("b"))
  println("=============单独获取key、value============")
  //获取key
  val keys = map.keys //前面一个占位符表示map的任意元素，后面一个表示任意元素的第一个元素，即 key
  keys.foreach(print)
  println()
  //获取value
  val valus = map.values //类似
  valus.foreach(print)
  println()
  println("===========将两个list转化为一个map===============")
  //反向操作可以使用zip，其中一个list作为key，另一个作为value
  val list = List(1, 2, 3, 4)
  //以少的为基准，不够舍弃
  val scores = list.zip(valus).toMap //list作为key,values作为value
  scores foreach { case (k, v) => println(s"$k$v") }

}
