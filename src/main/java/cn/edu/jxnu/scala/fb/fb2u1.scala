package cn.edu.jxnu.scala.fb

import scala.annotation.tailrec

/**
 * 写一个递归函数，获取第n个斐波那契数，前两个是0,1
 * 使用局部尾递归函数而不是循环
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-13
 */
object fb2u1 extends App {

    Console println fib(5) //等价 println(finb(5))，以后只会使用这种方法

    def fib(n: Int): Int = {

        /**
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
}
