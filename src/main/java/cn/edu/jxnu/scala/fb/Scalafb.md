## Scala 函数式学习例子

- 《Scala函数式编程》（小红书）练习题的个人实现，仅供参考

- object命名格式 fb2u1 表示函数式练习题第二章第一个，以此类推

- 源代码在 fb 包，此处不含包名

- 目前只提供习题部分

### 第二章

```scala
import scala.annotation.tailrec

/**
 * 写一个递归函数，获取第n个斐波那契数，前两个是0,1；使用局部尾递归函数而不是循环
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-13
 */
object fb2u1 extends App {

    Console println fib(5) //等价 println(finb(5))

    def fib(n: Int): Int = {

        /**
         *
         * @param n    第几个斐波那契数
         * @param ret1 第n个值
         * @param ret2 第n与第n+1的和
         * @return
         */
        @tailrec
        def go(n: Int, ret1: Int, ret2: Int): Int = {
            //注意是从0开始的斐波那契数列
            if (n == 1) ret1 else go(n - 1, ret2, ret1 + ret2)
        }

        go(n, 0, 1)
    }
}
```
```scala
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

        loop(0)
    }

}
```