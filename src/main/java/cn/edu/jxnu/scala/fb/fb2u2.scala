package cn.edu.jxnu.scala.fb

import scala.annotation.tailrec

/**
 * 实现isSorted方法，检测Array[A]是否按照给定的比较函数排序
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-15
 */
object fb2u2 extends App {

    //降序
    Console println isSorted[Int](Array(7, 6, 5, 4, 3, 2, 1), (n, m) => n > m)
    //升序
    Console println isSorted[Int](Array(1, 2, 3, 4, 5, 6, 7), (n, m) => n < m)

    /**
     *
     * @param as      数组
     * @param ordered 排序需要的比较方式，是个函数
     * @tparam A 泛型类型，测试一律使用Int
     * @return
     */
    def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
        @tailrec
        def loop(n: Int): Boolean = {
            if (n + 1 < as.length && ordered(as(n), as(n + 1))) loop(n + 1)
            else if (as.length - 1 == n) true
            else false
        }
        //启动调用，数组第一个下标是0
        loop(0)
    }
}
