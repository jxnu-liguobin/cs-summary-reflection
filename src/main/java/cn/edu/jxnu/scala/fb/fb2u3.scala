package cn.edu.jxnu.scala.fb

/**
 * 柯里化
 *
 * 2个参数转换为只有1个参数的部分应用函数
 *
 * 满足编译通过即可
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-17
 */
object fb2u3 {

    /**
     * 部分应用表示函数被应用的参数不是它所需要的完整的参数
     * 因为=>是右结合的，A=>(B=>C)后面的括号可去掉
     *
     * @param f
     * @tparam A
     * @tparam B
     * @tparam C
     * @return 返回类型A => (B => C)，需要参数A，参数B，并应用到函数f中
     */
    def curry[A, B, C](f: (A, B) => C): A => (B => C) = (a: A) => ((b: B) => f(a, b))

}
