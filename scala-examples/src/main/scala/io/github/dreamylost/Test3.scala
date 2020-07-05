/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

object Test3 extends App {
  val arr = new Array[String](3) //这种方式不是函数式编程推荐的
  // arr=new Array(2) val不能被重新赋值，但是本身指向的对象可能发生改变比如：改变arr数组内容
  arr(0) = "hello1" //实际上，Scala数组赋值也是函数调用，arr(i)底层调用了apply(i)方法，这是与其他方法调用一致的通用规则
  arr(1) = "hello2" //arr(i) = "hello" 实际上底层调用了update方法 arr.update(0,"hello")
  arr(2) = "hello3"
  arr.foreach(println)
}
