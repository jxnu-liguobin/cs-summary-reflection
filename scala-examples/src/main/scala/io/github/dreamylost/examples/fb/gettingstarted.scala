package io.github.dreamylost.examples.fb

import scala.annotation.tailrec

/**
 * 第二章
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-19
 */
object gettingstarted extends App {

  Console println fib(5) //等价 println(finb(5))，以后只会使用这种方法
  //降序
  Console println isSorted[Int](Array(7, 6, 5, 4, 3, 2, 1), (n, m) => n > m)
  //升序
  Console println isSorted[Int](Array(1, 2, 3, 4, 5, 6, 7), (n, m) => n < m)

  /**
   * 2.1：写一个递归函数，获取第n个斐波那契数，前两个是0,1
   *
   * @param n
   * @return
   */
  def fib(n: Int): Int = {

    /**
     * 使用局部尾递归函数而不是循环
     *
     * @param n    第几个斐波那契数
     * @param ret1 第n个值
     * @param ret2 第n与第n+1的和
     * @return
     */
    @tailrec //尾递归优化，不符合优化规则报错
    def go(n: Int, ret1: Int, ret2: Int): Int = {
      //注意是从0开始的斐波那契数列
      if (n == 1) ret1 else go(n - 1, ret2, ret1 + ret2)
    }

    go(n, 0, 1)
  }

  /**
   * 2.2：实现isSorted方法，检测Array[A]是否按照给定的比较函数排序
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

  /**
   * 2.3：柯里化
   *
   * 2个参数转换为只有1个参数的部分应用函数
   * 满足编译通过即可
   *
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

  /**
   *2.4：反柯里化
   *
   * 与柯里化相反。
   *
   * @param f
   * @tparam A
   * @tparam B
   * @tparam C
   * @return 返回类型(A, B) => C  需要a,b参数，返回值需要应用f函数两次，可以理解是柯里化调用两次
   */
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = (a: A, b: B) => f(a)(b)

  /**
   * 2.5：实现高阶函数，组合两个函数为一个函数
   *
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
