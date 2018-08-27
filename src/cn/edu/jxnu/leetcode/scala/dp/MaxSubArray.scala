package cn.edu.jxnu.leetcode.scala.dp

/**
  * @title: MaxSubArray
  * @description:
  * @author 梦境迷离
  * @date 2018-08-26
  */
object MaxSubArray extends App {

  //  val ret = maxSubArray(Array(-2, 1, -3, 4, -1, 2, 1, -5, 4))
  val ret = maxSubArray(Array(-2, 1, -3, 4, -1, 2, 1, -5, 4))
  println(ret)

  //测试，使用5以内的随机数生成10个元素
  val array = Array.fill(10) {
    scala.util.Random.nextInt(5)
  }

  for (i <- 0 until array.length) {
    print(array(i))
  }

  /**
    *
    * 子数组最之和的最大值
    */
  def maxSubArray(nums: Array[Int]): Int = {

    var nStart = nums(nums.length - 1)
    var nAll = nums(nums.length - 1)
    for (i <- (0 to nums.length - 2).reverse) {
      nStart = math.max(nums(i), nStart + nums(i))
      nAll = math.max(nStart, nAll)
    }
    nAll
  }


  /**
    * 数组最大递增子序列
    */
  def lis(nums: Array[Int]): Int = {

    val lis = new Array[Int](nums.length) //new不可以省略，这里是new对象，而不是使用已有数据构造数组
    for (i <- 0 until nums.length) {
      lis(i) = 1
      for (j <- 0 until i) {
        if (nums(i) > nums(j) && lis(j) + 1 > lis(i)) {
          lis(i) = lis(j) + 1
        }
      }
    }
    // 取得lis最大值
    var max = -1
    for (i <- 0 until lis.length) {
      max = math.max(max, lis(i))
    }
    max
  }

}
