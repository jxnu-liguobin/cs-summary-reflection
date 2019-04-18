package cn.edu.jxnu.scala.fb

/**
 * 实现高阶函数，组合两个函数为一个函数
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-18
 */
object fb2u5 {

    /**
     * 使用compose方法是作弊
     *
     * @param f
     * @param g
     * @tparam A
     * @tparam B
     * @tparam C
     * @return
     */
    def compose[A, B, C](f: B => C, g: A => B): A => C = (a: A) => f(g(a))

}
