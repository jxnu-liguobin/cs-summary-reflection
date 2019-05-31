## 《Scala 函数式编程》习题实现

- 《Scala函数式编程》（小红书）练习题的个人实现，仅供参考

- 源代码在 fb 包，此处不含包名

- 目前只含习题部分

### 章节内容
- [x] Chapter 2: gettingstarted:  
- [x] Chapter 3: datastructures:  
- [x] Chapter 4: errorhandling:   
- [x] Chapter 5: laziness:        
- [ ] Chapter 6: state:
- [ ] Chapter 7: parallelism:
- [ ] Chapter 8: testing: 
- [ ] Chapter 9: parsing: 
- [ ] Chapter 10: monoids:
- [ ] Chapter 11: monads:
- [ ] Chapter 12: applicative:
- [ ] Chapter 13: iomonad:
- [ ] Chapter 14: localeffects:
- [ ] Chapter 15: streamingio:

### 第二章

```scala
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
```

### 第三章

```scala
import cn.edu.jxnu.scala.fb.datastructures.List.foldRight

import scala.annotation.tailrec

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
    //左折叠实现
    Console println List.sum3(List(1, 2, 3, 4, 5, 6))
    Console println List.product3(List(1, 2, 3, 4, 5, 6))
    Console println List.length3(List(1, 2, 3, 4, 5, 6))
    //反转列表
    Console println List.reverse(List(1, 2, 3, 4, 5, 6))
    //使用右折叠实现append
    Console println List.appendViaFoldRight(List(1, 4), List(2, 3))
    //拼接列表
    Console println List.concat(List(List(1, 2), List(3, 4), List(2, 3)))
    //每个元素加1
    Console println List.add1(List(1, 2))
    //使用过滤，删除奇数
    Console println List.filter(List(1, 2, 3, 4, 5, 6))(_ % 2 == 1)
    //使用map（每个元素都变成List），与concat拼接
    Console println List.flatMap(List(1, 2, 3, 4, 5, 6))(i => List(i, i))
    //过滤
    Console println List.filterByFlatMap(List(1, 2, 3, 4, 5, 6))(_ % 2 == 1)
    //列表对应位相加
    Console println List.addPairwise(List(1, 2, 3, 4, 5, 6), List(1, 2, 3, 4, 5, 6))
    //判断子集合
    Console println List.hasSubsequence(List(1, 2, 3, 4, 5, 6), List(1, 2))


    //sealed表面本接口的实现类必须在当前文件中，且限定A是协变的，即List[A]是List[B]的子类，当且仅当A是B的子类
    sealed trait List[+A]

    //空的List是任何list的子类
    case object Nil extends List[Nothing]

    //非空的List必然是head+子集合
    case class Cons[+A](head: A, tail: List[A]) extends List[A]

    //列表操作
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

        //3.13：（使用foldLeft实现foldRight，避免栈溢出）
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

        /**
         * 3.16：对列表中每个元素进行加1操作
         *
         * @param list
         * @return
         */
        def add1(list: List[Int]): List[Int] = {
            //列表、初始值Nil、操作函数（List的尾部一定是Nil，用右折叠，第一个元素是已知）
            foldRight(list, Nil: List[Int])((h, t) => Cons(h + 1, t))
        }

        /**
         * 3.17：将列表的每个元素改为String类型
         *
         * @param list
         * @return
         */
        def doubleToString(list: List[Double]): List[String] = {
            foldRight(list, Nil: List[String])((h, t) => Cons(h.toString, t))
        }

        /**
         * 3.18：对列表中的每个元素进行修改，并维持列表结构
         *
         * @param as
         * @param f
         * @tparam A
         * @tparam B
         * @return
         */
        def map[A, B](as: List[A])(f: A => B): List[B] = {
            //            foldRightViaFoldLeft(as, Nil:List[B])((h,t) => Cons(f(h),t))
            foldRight(as, Nil: List[B])((h, t) => Cons(f(h), t))
        }

        /**
         * 3.19：从列表中删除不满足断言的元素，并用它删除一个List[Int]中所有奇数
         *
         * @param as
         * @param f
         * @tparam A
         * @return
         */
        def filter[A](as: List[A])(f: A => Boolean): List[A] = {
            foldRight(as, Nil: List[A])((h, t) => if (f(h)) Cons(h, t) else t)
        }

        /**
         * 3.20：与map相似，但是传入的f函数是返回列表，这个f返回的列表会被塞到flatMap最终返回的列表中
         *
         * @param as
         * @param f
         * @tparam A
         * @tparam B
         * @return
         */
        def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = {
            concat(map(as)(f))
        }

        /**
         * 3.21：使用flatMap实现filter
         *
         * @param as
         * @param f
         * @tparam A
         * @return
         */
        def filterByFlatMap[A](as: List[A])(f: A => Boolean): List[A] = {
            flatMap(as)(a => if (f(a)) List(a) else Nil)
        }

        /**
         * 3.22： 接收两个列表，对相应元素相加构造出新的列表
         *
         * @param a
         * @param b
         * @return
         */
        def addPairwise(a: List[Int], b: List[Int]): List[Int] = {
            (a, b) match {
                case (Nil, _) => Nil
                case (_, Nil) => Nil
                case (Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, addPairwise(t1, t2))
            }
        }

        /**
         * 3.23：与上面相同，进行泛化
         *
         * @param a
         * @param f
         * @tparam A
         * @return
         */
        def zipWith[A, B, C](a: List[A], b: List[B])(f: (A, B) => C): List[C] = (a, b) match {
            case (Nil, _) => Nil
            case (_, Nil) => Nil
            case (Cons(h1, t1), Cons(h2, t2)) => Cons(f(h1, h2), zipWith(t1, t2)(f))
        }

        /**
         * 3.24：检查一个List子序列是否包含另一个List
         *
         * @param sup
         * @param sub
         * @tparam A
         * @return
         */
        @tailrec
        def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
            //sup是空集时，当且仅当sub是空集，集合相等
            case Nil => sub == Nil
            case _ if startsWith(sup, sub) => true
            case Cons(h, t) => hasSubsequence(t, sub)
        }

        /**
         * 判断sub的元素是否均存在于sup
         *
         * @param sup
         * @param sub
         * @tparam A
         * @return
         */
        @tailrec
        def startsWith[A](sup: List[A], sub: List[A]): Boolean = {
            (sup, sub) match {
                //空集是任何集合的子集，此时sup还有元素
                case (_, Nil) => true
                //均是非空集
                case (Cons(h, t), Cons(h2, t2)) if h == h2 => startsWith(t, t2)
                case _ => false
            }
        }
    }


    /*=============未测试=========================*/
    //定义树结构
    sealed trait Tree[+A]

    //叶子
    case class Leaf[A](value: A) extends Tree[A]

    //非叶子
    case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

    //树（二叉树）
    //与List类似，Tree也只有两种情况，叶子与非叶子（非空树）
    object Tree {

        /**
         * 3.25：统计树的节点数
         *
         * @param t
         * @tparam A
         * @return
         */
        def size[A](t: Tree[A]): Int = t match {
            case Leaf(_) => 1
            //左右子树个数+1（root）
            case Branch(l, r) => 1 + size(l) + size(r)
        }

        /**
         * 3.26：获取Tree中最大的元素值
         *
         * @param t
         * @return
         */
        def maximum(t: Tree[Int]): Int = t match {
            case Leaf(l) => l
            case Branch(l, r) => maximum(l) max maximum(r)
        }

        /**
         * 3.27：计算根节点到叶子的最大深度
         *
         * @param t
         * @tparam A
         * @return
         */
        def depth[A](t: Tree[A]): Int = t match {
            //叶子深度为-
            case Leaf(l) => 0
            //左边最大深度+右边最大深度+1
            case Branch(l, r) => 1 + (depth(l) max depth(r))
        }

        /**
         * 3.28：类似List中map
         *
         * @param t
         * @tparam A
         * @tparam B
         * @return
         */
        def map[A, B](t: Tree[A])(f: A => B): Tree[B] = t match {
            case Leaf(n) => Leaf(f(n))
            case Branch(l, r) => Branch(map(l)(f), map(r)(f))
        }


        /**
         * 3.29 泛化上面三个函数，进一步抽象三个函数
         *
         * 与List类似，使用折叠
         *
         * @param t 需要操作的二叉树
         * @param f 入参是树的元素类型A，返回参数是具体操作函数g的参数类型B
         * @param g 具体操作函数
         * @tparam A
         * @tparam B
         * @return
         */
        def fold[A, B](t: Tree[A])(f: A => B)(g: (B, B) => B): B = t match {
            case Leaf(a) => f(a)
            //对左子树应用操作函数且对右子树应用操作函数
            case Branch(l, r) => g(fold(l)(f)(g), fold(r)(f)(g))
        }

        def sizeViaFold[A](t: Tree[A]): Int = {
            //对左右子树进行size操作，从1开始
            fold(t)(_ => 1)(1 + _ + _)
        }

        def maximumViaFold(t: Tree[Int]): Int = {
            //对左右子树进行max操作，从任意元素开始
            fold(t)(a => a)(_ max _)
        }

        def depthViaFold[A](t: Tree[A]): Int = {
            //对左右子树进行depth操作，从0开始
            fold(t)(_ => 0)((d1, d2) => 1 + (d1 max d2))
        }

    }

}
```

### 第四章

```scala
/**
 * 第四章
 *
 * @author 梦境迷离
 * @version 1.0, 2019-05-01
 */
object errorhandling extends App {

    //    Console println Option.failingFn(1)
    Console println Option.failingFn2(1)
    Console println Option.mean(Seq(1, 2, 3, 4))
    Console println Either.safeDiv(1, 0)

    //可选数据类型
    //函数放到特质中只是风格上的选择。以便使用 obj fn arg1 方式替代fn(obj,arg1)
    sealed trait Option[+A] {

        //4.1：Option基本函数实现
        //如果Option不为None，对其应用f函数
        def map[B](f: A => B): Option[B] = this match {
            case None => None
            case Some(a) => Some(f(a))
        }

        //如果Option不为None，返回实际值，否则返回默认值，default: => B表示非立即求值
        def getOrElse[B >: A](default: => B): B = this match {
            case None => default
            case Some(a) => a
        }

        //可能会失败
        def flatMap[B](f: A => Option[B]): Option[B] = {
            map(f) getOrElse None
        }


        //使用模式匹配
        def flatMap_1[B](f: A => Option[B]): Option[B] = this match {
            case None => None
            case Some(a) => f(a)
        }

        def orElse[B >: A](ob: => Option[B]): Option[B] = {
            this map (Some(_)) getOrElse ob
        }

        //使用模式匹配，B >: A 表示B的类型必须是A或者是A的超类类型
        def orElse_1[B >: A](ob: => Option[B]): Option[B] = this match {
            case None => ob
            case _ => this
        }

        def filter(f: A => Boolean): Option[A] = {
            flatMap(a => if (f(a)) Some(a) else None)
        }

        //使用模式匹配
        def filter_1(f: A => Boolean): Option[A] = this match {
            case Some(a) if f(a) => this
            case _ => None
        }

    }

    //非空
    case class Some[+A](get: A) extends Option[A]

    //空
    case object None extends Option[Nothing]

    object Option {
        //书上例子
        def failingFn(i: Int): Int = {
            val y: Int = throw new Exception("fail!")
            try {
                val x = 42 + 5
                x + y
            }
            catch {
                case e: Exception => 43
            }
        }

        //书上例子
        def failingFn2(i: Int): Int = {
            try {
                val x = 42 + 5
                //抛出的异常可以被赋予任何类型
                x + ((throw new Exception("fail!")): Int)
            }
            catch {
                case e: Exception => 43
            }
        }

        //书上例子
        def mean(xs: Seq[Double]): Option[Double] = {
            if (xs.isEmpty) None
            else Some(xs.sum / xs.length)
        }

        /**
         * 4.2：根据flatMap实现方差函数
         *
         * @param xs
         * @return
         */
        def variance(xs: Seq[Double]): Option[Double] = {
            //方差：对每个元素求math.pow(x-m,2)的累加和并/元素个数，标准差再开2次根号
            mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
        }

        /**
         * 4.3：使用一个二元函数组合两个Option值
         *
         * @param a
         * @param b
         * @param f
         * @tparam A
         * @tparam B
         * @tparam C
         * @return
         */
        def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
            a flatMap {
                aa => b map (bb => f(aa, bb))
            }
        }

        //for推导实现
        def map2_1[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
            for {
                aa <- a
                bb <- b
            } yield f(aa, bb)
        }

        /**
         * 4.4：将Option列表结合为一个Option
         *
         * @param a
         * @tparam A
         * @return
         */
        def sequence[A](a: List[Option[A]]): Option[List[A]] = {
            a match {
                case Nil => Some(Nil)
                case h :: t => h flatMap (hh => sequence(t) map (hh :: _)) //显示递归
            }
        }

        //使用右折叠，参考3.15的concat函数
        def sequence_1[A](a: List[Option[A]]): Option[List[A]] = {
            a.foldRight[Option[List[A]]](Some(Nil))((x, y) => map2(x, y)(_ :: _))
        }

        //组合所有Option元素
        def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
            a match {
                case Nil => Some(Nil)
                case h :: t => map2(f(h), traverse(t)(f))(_ :: _) //显示递归
            }
        }

        //使用右折叠
        def traverse_1[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
            a.foldRight[Option[List[B]]](Some(Nil))((h, t) => map2(f(h), t)(_ :: _))
        }

        /**
         * 4.5：使用map和sequence函数，只遍历一次列表
         *
         * @param a
         * @tparam A
         * @return
         */
        def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] = {
            traverse(a)(x => x)
        }
    }

    //包含异常信息，基本与Option相似
    sealed trait Either[+E, +A] {

        //4.6：Either的map、flatMap、orElse、map2，类似Option
        def map[B](f: A => B): Either[E, B] = {
            this match {
                case Right(a) => Right(f(a))
                case Left(e) => Left(e)
            }
        }

        //对右侧进行mapping时，必须限定左边的类型参数是E的父类型
        def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = {
            this match {
                case Left(e) => Left(e)
                case Right(a) => f(a)
            }
        }

        def orElse[EE >: E, AA >: A](b: => Either[EE, AA]): Either[EE, AA] = {
            this match {
                case Left(_) => b
                case Right(a) => Right(a)
            }
        }

        def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = {
            for {a <- this; b1 <- b} yield f(a, b1)
        }
    }

    //互斥并集。一般表示失败
    case class Left[+E](get: E) extends Either[E, Nothing]

    //一般表示成功
    case class Right[+A](get: A) extends Either[Nothing, A]

    object Either {

        //书上例子
        def mean(xs: IndexedSeq[Double]): Either[String, Double] = {
            if (xs.isEmpty)
                Left("mean of empty list!")
            else
                Right(xs.sum / xs.length)
        }

        //书上例子
        def safeDiv(x: Int, y: Int): Either[Exception, Int] = {
            try Right(x / y)
            catch {
                case e: Exception => Left(e)
            }
        }

        //书上例子
        def Try[A](a: => A): Either[Exception, A] = {
            try Right(a)
            catch {
                case e: Exception => Left(e)
            }
        }

        //4.7：类似Option
        def traverse[E, A, B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
            es match {
                case Nil => Right(Nil)
                case h :: t => (f(h) map2 traverse(t)(f)) (_ :: _)
            }
        }

        def traverse_1[E, A, B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
            es.foldRight[Either[E, List[B]]](Right(Nil))((a, b) => f(a).map2(b)(_ :: _))
        }

        def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = {
            traverse(es)(x => x)
        }
    }

    //4.8使用Either[List[E],_]
    //或使用新数据结构
    trait Partial[+A, +B]

    case class Errors[+A](get: Seq[A]) extends Partial[A, Nothing]

    case class Success[+B](get: B) extends Partial[Nothing, B]

}
```

### 第五章

```scala
/**
 * @author 梦境迷离
 * @version 1.0, 2019-05-18
 */
object laziness extends App {

    lazy val stream = Stream.apply(1, 2, 3, 4, 5, 6, 7)
    
    Console println "stream => " + stream
    Console println "headOption => " + stream.headOption
    Console println "toList => " + stream.toList
    Console println "take => " + stream.take(1)
    Console println "drop => " + stream.drop(1)
    Console println "takeWhile => " + stream.takeWhile(_.equals(1))
    Console println "exists => " + stream.exists(_.equals(2))
    Console println "exists2 => " + stream.exists2(_ == 2)
    Console println "forAll => " + stream.forAll(_ > 0)
    Console println "takeWhile2 => " + stream.takeWhile2(_.equals(1))
    Console println "map => " + stream.map(_ + 1) //对每个元素进行+1
    Console println "filter => " + stream.filter(_ > 3) //取出大于3的
    Console println "append => " + stream.append(Stream(6, 6, 6, 6, 6, 6)) //追加流
    Console println "flatMap => " + stream.flatMap(i => Stream(i, i)) //对每个元素都进行 i=>(i,i)的转化
    //只需处理当前元素所够用的内存，元素在被filter决定不再需时会被GC清理（大量的大元素处理时，这样会降低程序对内存的需求）
    Console println "find => " + stream.find(_ > 6)
    //    Console println "ones => " + Stream.ones
    Console println "mapWithUnfold => " + stream.mapWithUnfold(_.toString + " - ")
    Console println "takeWithUnfold => " + stream.takeWithUnfold(2)
    Console println "takeWhileWithUnfold => " + stream.takeWhileWithUnfold(_ > 0)
    Console println "startsWith => " + stream.startsWith(Stream(1, 2))
    //使用默认元素值填充
    Console println "zipAll-1 => " + stream.zipAll(Stream(1, 2))
    Console println "zipAll-2 => " + Stream(1, 2).zipAll(stream)
    //只对对应位置进行处理，长度较长的被忽略
    Console println "zipWith-1 => " + stream.zipWith(Stream(1, 2))(_ -> _)
    Console println "zipWith-2 => " + Stream(1, 2).zipWith(stream)(_ -> _)
    Console println "tails => " + stream.tails
    //子序列是包含结尾的，不是从中间取
    Console println "hashSubsequence => " + stream.hashSubsequence(Stream(6, 7))
    Console println "scanRight => " + stream.scanRight(0)(_ + _)


    import Stream._

    //定义数据结构
    //支持协变
    trait Stream[+A] {

        /**
         * 只是为了方便测试，自己加的toString方法
         *
         * @return
         */
        override def toString: String = {
            toList.toString()
        }

        //书上原有方法
        def headOption: Option[A] = this match {
            case Empty => None
            case Cons(h, t) => Some(h()) //显示调用thunk强制求值
        }

        /**
         * 5.1：将流转化为List
         *
         * @return
         */
        def toList: List[A] = {
            //类似头插法构造list，()用于强制求值
            @annotation.tailrec
            def loop(s: Stream[A], acc: List[A]): List[A] = s match {
                case Cons(h, t) => loop(t(), h() :: acc)
                case _ => acc
            }

            loop(this, List()).reverse
        }

        /**
         * 5.2-1：返回stream中前n个元素
         *
         * @param n
         * @return
         */
        def take(n: Int): Stream[A] = this match {
            case Cons(h, t) if n > 1 => cons(h(), t().take(n - 1))
            case Cons(h, _) if n == 1 => cons(h(), empty)
            case _ => empty
        }

        /**
         * 5.2-2：返回stream中第n个元素之后的所有元素
         *
         * @param n
         * @return
         */
        @annotation.tailrec
        final def drop(n: Int): Stream[A] = this match {
            case Cons(_, t) if n > 0 => t().drop(n - 1)
            case _ => this
        }

        /**
         * 5.3：返回stream中从起始元素连续满足给定断言的所有元素
         *
         * @param p
         * @return
         */
        def takeWhile(p: A => Boolean): Stream[A] = this match {
            //匹配case，同时满足if
            case Cons(h, t) if p(h()) => cons(h(), t().takeWhile(p))
            case _ => empty
        }

        //书上原有方法
        def exists(p: A => Boolean): Boolean = this match {
            case Cons(h, t) => p(h()) || t().exists(p) // ||有短路功能，第一个参数为true，不会再执行第二个表达式（方法）
            case _ => false
        }

        //书上原有方法，类似List的右折叠，=> B是传名参数
        def foldRight[B](z: => B)(f: (A, => B) => B): B = {
            this match {
                case Cons(h, t) => f(h(), t().foldRight(z)(f)) //如果f不对第二个参数求值，递归就不会发生
                case _ => z
            }
        }

        //书上原有方法，使用foldRight实现的exists
        def exists2(p: A => Boolean): Boolean = {
            foldRight(false)((a, b) => p(a) || b)
        }

        /**
         * 5.4：检查stream中所有元素是否与给定的断言匹配，遇到不匹配的值就终止遍历
         *
         * @param p
         * @return
         */
        def forAll(p: A => Boolean): Boolean = {
            //这里默认是true。注意
            foldRight(true)((h, t) => p(h) && t) //&& 可以短路，遇到不匹配就不执行递归了
        }

        /**
         * 5.5：使用foldRight实现
         *
         * @param p
         * @return
         */
        def takeWhile2(p: A => Boolean): Stream[A] = {
            //折叠初始值，折叠方式
            foldRight(empty[A])((h, t) => if (p(h)) cons(h, t) else empty)
        }

        /**
         * 5.6：使用foldRight实现
         *
         * @return
         */
        def headOption2: Option[A] = {
            //起始值是None，类型是Option
            foldRight(None: Option[A])((h, _) => Some(h))
        }

        /**
         * 5.7-1：使用foldRight实现
         *
         * @param f
         * @tparam B
         * @return
         */
        def map[B](f: A => B): Stream[B] = {
            foldRight(empty[B])((h, t) => cons(f(h), t))
        }

        /**
         * 5.7-2：使用foldRight实现
         *
         * @param f
         * @return
         */
        def filter(f: A => Boolean): Stream[A] = {
            foldRight(empty[A])((h, t) => if (f(h)) cons(h, t) else t)
        }


        /**
         * 5.7-3：使用foldRight实现
         *
         * @param s
         * @tparam B
         * @return
         */
        def append[B >: A](s: => Stream[B]): Stream[B] = {
            foldRight(s)((h, t) => cons(h, t))
        }

        /**
         * 5.7-4：使用foldRight实现
         *
         * @param f
         * @tparam B
         * @return
         */
        def flatMap[B](f: A => Stream[B]): Stream[B] = {
            foldRight(empty[B])((h, t) => f(h) append t)
        }

        //书上原有方法
        def find(p: A => Boolean): Option[A] = {
            filter(p).headOption
        }

        /**
         * 5.13-1：使用unfold实现map
         *
         * @param f
         * @tparam B
         * @return
         */
        def mapWithUnfold[B](f: A => B): Stream[B] =
            unfold(this) {
                //使用f函数将h进行类型转换生成值 f(h() ，继续对tail流操作
                case Cons(h, t) => Some((f(h()), t()))
                case _ => None
            }

        /**
         * 5.13-2：使用unfold实现take
         *
         * @param n
         * @return
         */
        def takeWithUnfold(n: Int): Stream[A] = {
            unfold((this, n)) {
                case (Cons(h, t), 1) => Some((h(), (empty, 0)))
                case (Cons(h, t), n) if n > 1 => Some((h(), (t(), n - 1)))
                case _ => None
            }
        }

        /**
         * 5.13-3：使用unfold实现takeWhile
         *
         * @param f
         * @return
         */
        def takeWhileWithUnfold(f: A => Boolean): Stream[A] = {
            unfold(this) {
                case Cons(h, t) if f(h()) => Some((h(), t()))
                case _ => None
            }
        }

        /**
         * 封装了zipWith
         *
         * @param s2
         * @tparam B
         * @return
         */
        def zip[B](s2: Stream[B]): Stream[(A, B)] = {
            zipWith(s2)((_, _))
        }

        /**
         * 5.13-4：使用unfold实现zipWith
         *
         * 接收一个stream，对两个stream的对应元素使用f函数进行处理，构造出新的stream
         * zip函数将传进来的两个参数中相应位置上的元素组成一个pair数组
         * 如果其中一个参数元素比较长，那么多余的参数会被删掉。
         *
         * @param s2
         * @param f
         * @tparam B
         * @tparam C
         * @return
         */
        def zipWith[B, C](s2: Stream[B])(f: (A, B) => C): Stream[C] = {
            unfold((this, s2)) {
                case (Cons(h1, t1), Cons(h2, t2)) => Some((f(h1(), h2()), (t1(), t2())))
                case _ => None
            }
        }

        /**
         * 5.13-5：使用unfold实现zipAll
         *
         * zipAll应该继续遍历只要stream还有更多元素
         * 和zip函数类似，区别：如果其中一个元素个数比较少，那么将用默认的元素填充(None)
         *
         * @param s2
         * @tparam B
         * @return
         */
        def zipAll[B](s2: Stream[B]): Stream[(Option[A], Option[B])] = {
            zipWithAll(s2)(_ -> _)
        }

        def zipWithAll[B, C](s2: Stream[B])(f: (Option[A], Option[B]) => C): Stream[C] = {
            unfold((this, s2)) {
                //二元组表达式除了标准的小括号表达方式：("a","b")，还可以有箭头表达式："a"->"b"
                //1) ArrowAssoc是值类型，运行时虽有隐式转换，但避免了在堆上分配一个包装对象
                //2) ->方法是声明为@inline的，也提升了性能
                case (Empty, Empty) => None
                case (Cons(h, t), Empty) => Some(f(Some(h()), Option.empty[B]) -> (t() -> empty[B]))
                case (Empty, Cons(h, t)) => Some(f(Option.empty[A], Some(h())) -> (empty[A] -> t()))
                case (Cons(h1, t1), Cons(h2, t2)) => Some(f(Some(h1()), Some(h2())) -> (t1() -> t2()))
            }
        }

        /**
         * 5.14：使用已经存在的函数实现。
         *
         * 检查一个stream是否是另一个stream的前缀
         *
         * Example：Stream(1,2,3).startsWith(Stream(1,2))  return true
         *
         * @param s
         * @tparam A
         * @return
         */
        def startsWith[A](s: Stream[A]): Boolean = {
            zipAll(s).takeWhile(_._2.isDefined) forAll {
                case (h1, h2) => h1.get == h2.get
            }
        }

        /**
         * 5.15：使用unfold实现tails
         *
         * Example：Stream(1,2,3).tails   return  Stream(Stream(1,2,3),Stream(2,3),Stream(3),Stream())
         *
         * @return
         */
        def tails: Stream[Stream[A]] = {
            unfold(this) {
                case Empty => None
                //返回1,2,3调用drop，返回第一个元素之后的2,3
                //返回2,3 调用drop，返回第一个元素之后的3
                //返回3 调用 drop，返回第一个元素之后的()
                case s => Some((s, s drop 1))
            } append Stream(empty)
        }

        def hashSubsequence[A](s: Stream[A]): Boolean = {
            //取出第一个元素后与传进来的流进行首匹配
            tails exists (_ startsWith s)
        }

        /**
         * 5.16：泛化tails函数，类似foldRight返回一个中间结果的stream
         *
         * Example：Stream(1,2,3).scanRight(0)(_ + _).toList   return List(6,5,3,0)
         *
         * 难  看答案吧
         *
         * @param z
         * @param f
         * @tparam B
         * @return
         */
        def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] = {
            //初始值=(0,Stream()) ，操作 f= ( _ + _)
            foldRight(z -> Stream(z))((a, p0) => {
                //p0=p1=(27,List(27, 25, 22, 18, 13, 7, 0))
                lazy val p1: (B, Stream[B]) = p0 // 对于p0又能继续使用函数(_ + _)处理
                // 对p1._1与a 使用 _ + _ 进行处理
                val b2 = f(a, p1._1) // 得到临时值
                (b2, cons(b2, p1._2)) //继续与被调用与后续流相加
            })._2
        }
    }

    //空流
    case object Empty extends Stream[Nothing]

    //非空，由h+t组成，()不能省略
    case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

    //不需要使用this，不是对指定流进行处理的，用于构造流的方法写在object，作为“静态方法”
    object Stream {

        //流自带的智能构造方法（普通方法）
        //构造非空流
        def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
            lazy val head = hd
            lazy val tail = tl
            Cons(() => head, () => tail)
        }

        //创建空流
        def empty[A]: Stream[A] = Empty

        //根据多个元素构造流
        def apply[A](as: A*): Stream[A] = {
            if (as.isEmpty) empty
            else cons(as.head, apply(as.tail: _*))
        }

        //别测试无限流。。out of memory
        val ones: Stream[Int] = Stream.cons(1, ones)

        /**
         * 5.8：根据给定值返回无限流
         *
         * @param a
         * @tparam A
         * @return
         */
        def constant[A](a: A): Stream[A] = {
            //引用自身，是无限流，因为具有增量性质，上面写的函数对无限流也适用。但最后可能造成栈溢出
            lazy val tail: Stream[A] = Cons(() => a, () => tail)
            tail
        }

        /**
         * 5.9：写一个函数生成一个整数无限流，从N开始，然后N+1,N+2,N+3... Scala的Int是有符号32位整型
         * stream从某点开始从正数变为负数，并且40亿后会重复发生
         *
         * @param n
         * @return
         */
        def from(n: Int): Stream[Int] = {
            //从N开始，每个tail构造使用N+1
            cons(n, from(n + 1))
        }

        /**
         * 5.10：写一个fibs函数生成斐波那契熟练的无限流：0,1,1,2,3,5,8，等
         */
        def fibs = {
            def go(f0: Int, f1: Int): Stream[Int] = {
                //构造的时候使用f0+f1
                cons(f0, go(f1, f0 + f1))
            }

            go(0, 1)
        }

        /**
         * 5.11：写一个更加通用的构造流的函数，它接收一个起始状态，以及一个在生成的Stream中用于产生下一状态和下一个值的函数
         * 注：fold 可以根据数据源和条件，由包含多个元素的序列产生一个结果；而 unfold 方向相反，它根据条件由源产生了更多的结果
         *
         * 它有两个优点：
         * 1.消除了while循环语句
         * 2.消除了不必要的变量声明
         *
         * 具体参考答案，这个地方不太好理解，而且不好测无限流
         *
         * @param z 初始化值
         * @param f 传入S类型参数，产生下一值的函数
         * @tparam A 产生值的类型
         * @tparam S 初始值的类型
         * @return
         */
        def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
            //共递归函数
            //对z这个初始化先应用f函数
            f(z) match {
                //(h, s)是一个元组，使用f生成h（f在参与模式匹配时对第一个参数进行了处理），并继续对s执行同样操作
                case Some((h, s)) => cons(h, unfold(s)(f))
                case None => empty
            }

        }

        /**
         * 5.12-1：使用unfold实现fibs
         */
        def fibsWithUnfold = {
            //z:S=(f0, f1) => z:S=(f1,f0+f1)
            unfold((0, 1)) { case (f0, f1) => Some((f0, (f1, f0 + f1))) }
        }

        /**
         * 5.12-2：使用unfold实现from
         *
         * @param n
         * @return
         */
        def fromWithUnfold(n: Int) = {
            unfold(n)(n => Some((n, n + 1)))
        }

        /**
         * 5.12-3：使用unfold实现constant
         *
         * @param a
         * @tparam A
         * @return
         */
        def constantWithUnfold[A](a: A) = {
            unfold(a)(_ => Some((a, a)))
        }

        /**
         * 5.12-4：使用unfold实现ones
         *
         * @return
         */
        def onesWithUnfold = {
            unfold(1)(_ => Some((1, 1)))
        }
    }

}
```

