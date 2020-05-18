package io.github.dreamylost

object Test4 extends App {

  for (i <- 0 to 2) //to包含了右边界  to实际是(0).to(2)的缩写，to返回一种包含了0、1、2的序列。对于单参数方法的调用时  () . 可以被省略
    println(i)
  println("==========================")
  for (i <- 0 until 2) //until不包含右边界 其他同
    // println(i)
    Console println i //省略括号需要显示的给出方法调用的目标对象才有效，此时println是操作符
  //实际上在Scala中任何操作符都是一种方法调用，而任何方法也可以是操作符，但是在多参数的情况下，操作符表示法必须用括号:strings indexOf ('a',startIndex)
  val s = "hello"
  s toLowerCase; //无参，无副作用不用括号,使用后缀需要隔断，用分号
  println() //有副作用用括号
  var num = -1 //-是前缀操作符，实际也是方法调用 可用的前缀操作符：！ + - ~  都是一元的
  var num2 = 1.unary_- // unary_是混合操作符 yield在Scala是关键字，需要使用反引号`yield`，其他如match类似
  if (num == num2)
    println(true) //true 都是-1，Scala的==比较的是值的相等性，不同于Java的==（比较引用的地址或者基本类型的值），但是Scala的值比较自动处理null
  //且样例类可以直接使用值比较（==），因为样例类实现了很多譬如：equals、toString等方法 @T
  //若想要比较引用地址可用eq/ne方法，不过这只对Scala对象直接映射到Java对象的对象有效。比如String
  println("======================new eq=====================")
  val str1 = new String("hello")
  val str2 = new String("hello")
  var str3 = "hello"
  var str4 = "hello"
  if (str1 eq str2) println("比较地址") //无输出
  if (str1 == str2) println("==比较内容") //输出
  if (str1 equals str2) println("equals比较内容") //输出
  println("======================常量 eq=====================")
  if (str3 eq str4) println("比较地址") //输出，指向同一个常量池的引用
  if (str3 == str4) println("==比较内容") //输出
  if (str3 equals str4) println("equals比较内容") //输出

}
