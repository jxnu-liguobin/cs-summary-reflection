/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 给定一个非负整数 num。对于 0 ≤ i ≤ num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。
  *
  * @author 梦境迷离
  * @date 2018-08-27
  */
object Leetcode_338 extends App {

  val ret = countBits(85723)
  for (i <- ret) {
    print(i)
  }

  /**
    * 524 ms,80.00%
    * 49.8 MB,100.00%
    * f[1] = (f[0]==0) + (1%1==1) = 1
    * f[11] = (f[1]==1) + (11%1==1)  = 2
    * f[110] = (f[11]==2) + (110%1==0) = 2
    * f[1101] = (f[110] ==2) + (1101%1==1) =3;
    */
  def countBits(num: Int): Array[Int] = {
    val f = new Array[Int](num + 1);
    for (i <- 1 to num) {
      f(i) = f(i >> 1) + (i & 1)
    }
    f
  }

  /**
    * 最后用例超时
    */
  def countBits2(num: Int): Array[Int] = {
    var ret = new Array[Int](num + 1)
    for (i <- 0 to num) {
      ret(i) = count(i)
    }
    ret
  }

  def count(n: Int): Int = {
    import scala.util.control.Breaks.break
    import scala.util.control.Breaks.breakable
    var nums = n
    var count = 0
    while (nums != 0) {
      breakable {
        if ((nums & 1) == 1) {
          count += 1
          break()
        }
      }
      nums >>>= 1
    }
    count
  }
}
