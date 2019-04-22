package cn.edu.jxnu.scala.fb

import cn.edu.jxnu.scala.fb.datastructures.List.foldRight

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
    //修改头元素
    Console println List.setHead(List(1, 2, 3, 4, 5, 6), 2)
    //删除前2个元素
    Console println List.drop(List(1, 2, 3, 4, 5, 6), 2)
    //删除列表中大于1的元素（或满足前缀判断的）
    Console println List.dropWhile[Int](List(1, 2, 3, 4, 5, 6), a => a > 5) //未知原因，＞无法生效
    Console println List.dropWhile[String](List("hello", "hello111", "hello444", "world"), a => a.startsWith("hello"))
    //柯里化的，第一个参数传入时返回一个函数，并使用第二个参数作为本函数的入参
    Console println List.dropWhile2(List("hello", "hello111", "hello444", "world2"))(a => a.startsWith("hello"))
    //将a2复制到a1后面
    Console println List.append(List(1, 4), List(2, 3))
    //删除最后一个元素
    Console println List.init(List(1, 2, 3, 4, 5, 6))
    //对foldRight传入Nil和Cons
    Console println foldRight(Cons(1, Cons(2, Cons(3, Nil))), Nil: List[Int])(Cons(_, _))
    Console println Cons(1, foldRight(Cons(2, Cons(3, Nil)), Nil: List[Int])(Cons(_, _)))
    Console println Cons(1, Cons(2, foldRight(Cons(3, Nil), Nil: List[Int])(Cons(_, _))))
    //计算List的长度
    Console println List.length(List(1, 2, 3, 4, 5, 6))

    Console println List.sum3(List(1, 2, 3, 4, 5, 6))
    Console println List.product3(List(1, 2, 3, 4, 5, 6))
    Console println List.length3(List(1, 2, 3, 4, 5, 6))
    //反转列表
    Console println List.reverse(List(1, 2, 3, 4, 5, 6))
    //使用右折叠实现append
    Console println List.appendViaFoldRight(List(1, 4), List(2, 3))
    //拼接列表
    Console println List.concat(List(List(1, 2), List(3, 4), List(2, 3)))


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
         * 3.1：List 数据构造器的模式匹配
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
         * 3.2：实现tail函数，删除一个List的第一个元素.时间开销是常量级，如果是Nil，在实现的时候有什么不同的选择？
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

        /**
         * 3.3：使用与3.1相同思路实现用一个不同的值替代列表中第一个元素
         *
         * @param list
         * @param a
         * @tparam A
         * @return
         */
        def setHead[A](list: List[A], a: A): List[A] = {
            list match {
                case Nil => sys.error("空列表的尾无法操作")
                case Cons(_, t) => Cons(a, t)
            }
        }

        /**
         * 3.4：把tail泛化为drop函数，用于从列表中删除前n个元素。时间开销与drop的元素个数成正比（不能复制列表）
         *
         * @param list
         * @param n
         * @tparam A
         * @return
         */
        def drop[A](list: List[A], n: Int): List[A] = {
            //使用了临时变量，不好
            //            var ll = list
            //            for (i <- 0 until n) {
            //                ll = tail(ll)
            //            }
            //            ll
            if (n <= 0) list
            else list match {
                case Nil => Nil
                case Cons(_, t) => drop(t, n - 1) //转化为删除n-1个元素
            }
        }

        /**
         * 3.5：删除列表中前缀符合判断的元素
         *
         * @param list
         * @param f
         * @tparam A
         * @return
         */
        def dropWhile[A](list: List[A], f: A => Boolean): List[A] = {
            list match {
                case Cons(h, t) if f(h) => dropWhile(t, f) //转化为对每个头元素进行判断
                case _ => list
            }
        }

        /**
         * 3.5：改进高阶函数的类型推导，调用时不需要再使用声明第二个参数的类型
         *
         * 因为参数组里的类型信息会从第一个参数传递到第二个参数，因为第一个参数类型是Int，第二个也为Int
         *
         * @param list
         * @param f
         * @tparam A
         * @return
         */
        def dropWhile2[A](list: List[A])(f: A => Boolean): List[A] = {
            list match {
                case Cons(h, t) if f(h) => dropWhile(t, f) //转化为对每个头元素进行判断
                case _ => list
            }
        }

        /**
         * 书上原定义方法
         *
         * @param a1 被解开，并放在a2的前面
         * @param a2
         * @tparam A
         * @return
         */
        def append[A](a1: List[A], a2: List[A]): List[A] = {
            a1 match {
                case Nil => a2
                case Cons(h, t) => Cons(h, append(t, a2))
            }
        }

        /**
         * 3.6：返回除最后一个元素之外的所有元素
         *
         * @param list
         * @tparam A
         * @return
         */
        def init[A](list: List[A]): List[A] = {
            list match {
                case Nil => sys.error("空列表的尾无法操作")
                case Cons(_, Nil) => Nil
                case Cons(h, t) => Cons(h, init(t))
            }
        }

        /**
         * 书上原定义方法
         *
         * 右折叠简单运用
         *
         * @param as
         * @param z 起始值
         * @param f 独立出来，让类型系统推出f的输入类型
         * @tparam A 元素类型
         * @tparam B 起始值类型
         * @return
         */
        def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = {
            as match {
                case Nil => z
                case Cons(x, xs) => f(x, foldRight(xs, z)(f))
            }
        }

        def sum2(ns: List[Int]): Int = {
            //foldRight函数不是面向特定的元素类型，泛化的类型不必一定与List中的元素类型相同
            foldRight(ns, 0)((x, y) => x + y)
        }

        def product2(ns: List[Double]): Double = {
            foldRight(ns, 1.0)(_ * _) //(_ * _)是(x,y) => x*y的简写
        }

        //3.7：问题有点难，第五章再来回顾

        //3.8：对foldRight传入Nil和Cons
        foldRight(Cons(1, Cons(2, Cons(3, Nil))), Nil: List[Int])(Cons(_, _))
        Cons(1, foldRight(Cons(2, Cons(3, Nil)), Nil: List[Int])(Cons(_, _)))
        Cons(1, Cons(2, foldRight(Cons(3, Nil), Nil: List[Int])(Cons(_, _))))
        Cons(1, Cons(2, Cons(3, foldRight(Nil, Nil: List[Int])(Cons(_, _)))))
        Cons(1, Cons(2, Cons(3, Nil)))

        /**
         * 3.9：使用foldRight计算LIst的长度
         *
         * @param as
         * @tparam A
         * @return
         */
        def length[A](as: List[A]): Int = {
            //每存在一个元素对acc进行加1，右折叠从1开始
            foldRight(as, 1)((_, acc) => acc + 1)
        }

        /**
         * 3.10：使用尾递归实现，防止List太大造成StackOverflow
         *
         * @param as
         * @param z
         * @param f
         * @tparam A
         * @tparam B
         * @return
         */
        def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = {
            as match {
                case Nil => z
                case Cons(h, t) => foldLeft(t, f(z, h))(f)
            }
        }

        /**
         * 3.11-1：使用foldLeft实现
         *
         * @param ns
         * @return
         */
        def sum3(ns: List[Int]): Int = {
            foldLeft(ns, 0)(_ + _)
        }

        /**
         * 3.11-2：使用foldLeft实现
         *
         * @param ns
         * @return
         */
        def product3(ns: List[Double]): Double = {
            foldLeft(ns, 1.0)(_ * _)
        }

        /**
         * 3.11-3：使用foldLeft实现
         *
         * @param as
         * @tparam A
         * @return
         */
        def length3[A](as: List[A]): Int = {
            //左折叠从0开始，第一个参数是B，第二个才是A，acc是长度计算，_是元素
            foldLeft(as, 0)((acc, _) => acc + 1)
        }

        /**
         * 3.12：反转列表。使用一个折叠实现
         *
         * @param list
         * @tparam A
         * @return
         */
        def reverse[A](list: List[A]): List[A] = {
            //默认传入空列表，elements参数是返回类型，h参数是A元素。相当于每次做链接的头插入操作
            foldLeft(list, List[A]())((elements, h) => Cons(h, elements))
        }

        //3.13：使用foldRight实现foldLeft。（使用foldLeft实现foldRight，避免栈溢出）
        def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B = {
            foldLeft(reverse(l), z)((b, a) => f(a, b))
        }

        def foldRightViaFoldLeft_1[A, B](l: List[A], z: B)(f: (A, B) => B): B = {
            foldLeft(l, (b: B) => b)((g, a) => b => g(f(a, b)))(z)
        }

        //我们正在调用“foldright”，将“b”类型实例化为“b=>b”，然后
        //使用'z'参数调用生成的函数。尝试用等号替换等号来扩展定义。
        //使用一个简单的例子，比如“foldLeft（list（1,2,3），0）”注意，这些实现更重要的是理论上的兴趣——它们不安全，也不适用于大的列表
        def foldLeftViaFoldRight[A, B](l: List[A], z: B)(f: (B, A) => B): B = {
            foldRight(l, (b: B) => b)((a, g) => b => g(f(b, a)))(z)
        }

        /**
         * 3.14：根据foldLeft或者foldRight实现append函数
         *
         * @param l
         * @param r
         * @tparam A
         * @return
         */
        def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] = {
            //列表，初始值r，拼接方法是构造函数
            foldRight(l, r)(Cons(_, _))
        }

        /**
         * 3.15：写一个函数将一组列表连接成一个单个列表。它的运行效率应该随着所有列表的总长度线性增长，试着用我们定义过的函数。
         *
         * @param l
         * @tparam A
         * @return
         */
        def concat[A](l: List[List[A]]): List[A] = {
            //列表集，初始值列表Nil，拼接函数append
            foldRight(l, Nil: List[A])(append)
        }
    }

}
