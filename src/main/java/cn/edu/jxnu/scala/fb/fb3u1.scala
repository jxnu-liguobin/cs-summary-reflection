package cn.edu.jxnu.scala.fb


/**
 * List 数据构造器的模式匹配
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-18
 */
object fb3u1 extends App {


    /**
     * 注意是右结合，调用顺序是反的
     * ::  {{{1 :: List(2, 3) = List(2, 3).::(1) = List(1, 2, 3)}}}
     *
     * ::: {{{List(1, 2) ::: List(3, 4) = List(3, 4).:::(List(1, 2)) = List(1, 2, 3, 4)}}}
     */
    val x = List(1, 2, 3, 4, 5, 6) match {
        case x :: 2 :: List(3, 4, _) => x //匹配 1 2 3 4 5 = 1
        case x :: y :: 3 :: 4 :: _ => x + y //匹配 1 2 3 4 5 6 = 3
        case l: List[Int] => l.sum //匹配 整型列表 并求和 (前面若已经匹配到，不会执行到这)
        case _ => 101 //未匹配
    }

    val y = 1 :: Nil match {
        case _ :: Nil => 101 //匹配 空
    }
    val z = 1 :: List() match {
        case _ :: List() => 101 //匹配 空  相同
    }

    println(x)
    println(y)
    println(z)

}
