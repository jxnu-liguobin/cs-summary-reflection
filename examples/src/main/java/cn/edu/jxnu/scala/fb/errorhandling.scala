package cn.edu.jxnu.scala.fb

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
