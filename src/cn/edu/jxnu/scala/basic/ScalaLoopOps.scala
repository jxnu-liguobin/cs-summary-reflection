package cn.edu.jxnu.scala.basic

import scala.util.control.Breaks._

/**
  * Java是指令式风格，Scala是函数式风格。
  * 在Scala中，应该尽量适用循环，而是应用函数的方式来处理。
  * Scala并没有提供break和continue语句来退出循环，那么如果我们又确实要怎么办呢，有如下几个选项：
  * 1. 使用Boolean型的控制变量。
  * 2. 使用嵌套函数，从函数当中return
  * 3. 使用Breaks对象中的break方法(这里的控制权转移是通过抛出和捕获异常完成的，尽量避免使用这套机制)
  * 4. 递归函数重写循环
  */
object ScalaLoopOps {

    def main(args: Array[String]) = {
        val list = List(21, 214, -4352, 65436, 7534)
        val list2 = List(212, 2134, -43522, 651436, 7534)
        for (i <- list.diff(list2)) {
            //差集
            print(i + " ")
        }
        println()

        for (i <- list.intersect(list2)) {
            //交集
            print(i + " ")
        }
        println()


        breakTest1(list)
        breakTest2(list)
        contniueTest1(list)
        continueTest2(list)
        continueTest3(list)

    }

    def breakTest1(list: List[Int]) = {
        println("breakable语句块来实现break操作")
        breakable {
            for (elem <- list)
                if (elem < 0) break else println(elem)
        }
    }

    def breakTest2(list: List[Int]) = {
        println("增加一个boolean变量作为for循环守卫，while循环同理")
        var foundMinus = false
        for (elem <- list if !foundMinus) {
            if (elem < 0) foundMinus = true else println(elem)
        }
    }

    def contniueTest1(list: List[Int]) = {
        println("breakable语句块来实现continue操作")
        for (elem <- list)
            breakable {
                if (elem < 0) break else println(elem)
            }
    }

    def continueTest2(list: List[Int]) = {
        println("if else控制来实现continue操作")
        for (elem <- list)
            if (elem < 0) () else println(elem)
    }

    def continueTest3(list: List[Int]) = {
        println("递归函数重写循环")

        def next(i: Int): Unit = {
            if (i >= list.size) Unit
            else if (list(i) < 0) next(i + 1)
            else println(list(i));
            next(i + 1)
        }

        next(0)

    }
}