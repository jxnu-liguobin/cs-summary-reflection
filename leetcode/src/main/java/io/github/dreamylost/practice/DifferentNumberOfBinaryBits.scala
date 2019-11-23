package io.github.dreamylost.practice

import scala.io.StdIn

/**
 * 两个整数的二进制有多少不同位
 * @author 梦境迷离.
 * @time 2018年8月2日
 * @version v1.0
 */
object DifferentNumberOfBinaryBits extends App {

    /**
     * 两个数进行异或，将异或的结果与其减一进行与操作，直至为零，就是二进制不同位数的数量。
     */
    //val n = Console.readLine()  过期方法
    val n = StdIn.readInt()
    val m = StdIn.readInt()
    var num = n ^ m
    var count = 0
    while (num != 0) {
        count += 1
        num = (num - 1) & num
    }
    println(count)
}