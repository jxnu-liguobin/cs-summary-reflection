package io.github.dreamylost.examples.fb

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
