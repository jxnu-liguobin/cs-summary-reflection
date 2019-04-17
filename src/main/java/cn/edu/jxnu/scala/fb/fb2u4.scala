package cn.edu.jxnu.scala.fb

/**
 * 反柯里化
 *
 * 与柯里化相反。
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-17
 */
object fb2u4 {

    /**
     *
     * @param f
     * @tparam A
     * @tparam B
     * @tparam C
     * @return 返回类型(A, B) => C  需要a,b参数，返回值需要应用f函数两次，可以理解是柯里化调用两次
     */
    def uncurry[A, B, C](f: A => B => C): (A, B) => C = (a: A, b: B) => f(a)(b)

}
