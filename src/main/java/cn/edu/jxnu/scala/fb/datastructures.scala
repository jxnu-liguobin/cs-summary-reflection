package cn.edu.jxnu.scala.fb

/**
 * 第三章
 *
 * @author 梦境迷离
 * @version 1.0, 2019-04-19
 */
object datastructures extends App {

    //打印x
    Console println List.x
    //打印删除第一个元素
    Console println List.tail(List(1, 2, 3, 4, 5, 6))

    //sealed表面本接口的实现类必须在当前文件中，且限定A是协变的，即List[A]是List[B]的子类，当且仅当A是B的子类
    sealed trait List[+A]

    //空的List是任何list的子类
    case object Nil extends List[Nothing]

    //非空的List必然是head+子集合
    case class Cons[+A](head: A, tail: List[A]) extends List[A]

    object List {

        /**
         * 书上原定义方法
         *
         * @param ints
         * @return
         */
        def sum(ints: List[Int]): Int = ints match {
            case Nil => 0
            case Cons(x, xs) => x + sum(xs)
        }

        /**
         * 书上原定义方法
         *
         * @param ds
         * @return
         */
        def product(ds: List[Double]): Double = ds match {
            case Nil => 1.0
            case Cons(0.0, _) => 0.0
            case Cons(x, xs) => x * product(xs)
        }

        /**
         * 书上原定义方法
         *
         * @param as
         * @tparam A
         * @return
         */
        def apply[A](as: A*): List[A] = {
            if (as.isEmpty) Nil
            else Cons(as.head, apply(as.tail: _*)) //注意这里是用: _*，表示将集合作为可变参数传递，而不是集合整体
        }

        /**
         * List 数据构造器的模式匹配
         *
         * 引申：注意是右结合，调用顺序是反的。目前使用的Scala2.12
         * ::  {{{1 :: List(2, 3) = List(2, 3).::(1) = List(1, 2, 3)}}}
         *
         * ::: {{{List(1, 2) ::: List(3, 4) = List(3, 4).:::(List(1, 2)) = List(1, 2, 3, 4)}}}
         */
        val x = List(1, 2, 3, 4, 5) match {
            case Cons(x, Cons(2, Cons(4, _))) => x
            case Nil => 42
            case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
            case Cons(h, t) => h + sum(t)
            case _ => 101
        }

        /**
         * 3.1：实现tail函数，删除一个List的第一个元素.时间开销是常量级，如果是Nil，在实现的时候有什么不同的选择？
         *
         * @param list
         * @tparam A
         * @return
         */
        def tail[A](list: List[A]): List[A] = {
            list match {
                case Nil => sys.error("空列表的尾无法获取")
                case Cons(_, t) => t
            }
        }
    }

}
