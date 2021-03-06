/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.examples

/**
  * @author 梦境迷离
  * @since 2019-12-27
  * @version v1.0
  */
object MapExamples extends App {
  val paramsFilterWithValues = Map("a" -> Seq("b"))
  val userFilterWithValues = Map[String, Seq[String]]("a" -> Seq("cc"), "b" -> Seq("qw"))
  //使用foldLeft合并两个Map
  val mergeMap = (userFilterWithValues foldLeft paramsFilterWithValues)((map, kv) => {
    //    第一个参数userFilterWithValues，迭代值
    //    第二个参数是paramsFilterWithValues，是默认值，Map类型
    map + (kv._1 -> (kv._2 ++ map.getOrElse(kv._1, Seq.empty[String])))
  })

  println(mergeMap)

  /** *
    *
    * 左折叠源码
    * def foldLeft[B](z: B)(op: (B, A) => B): B = {
    * var result = z
    * this foreach (x => result = op(result, x))
    * result
    * }
    *
    * 可见this为空时，不会执行foreach，直接返回z（即默认值）
    * 否则每次都执行传进的op函数，并返回result，随着result不断更新，最终得到一个折叠后的值。具体操作依赖于op函数。
    * 折叠函数是一个抽象的高阶函数，作用强大。由此可见result定义为var也有其意义的，如果为val就不能每次遍历时更新result。
    * *
    * 从实现上看，Scala的函数式并非完全的纯函数式，因为其变量并不是都为不可变的，而是通过一些技术上的手段，将可变变量隐藏在其内部实现，使得用户看不到可变状态。
    * 平时在编程中也应该尽量少的使用var和可变数据结构。
    */
}
