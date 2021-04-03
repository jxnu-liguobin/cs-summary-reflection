/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

//给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
//
// 示例:
//
// 给定 nums = [2, 7, 11, 15], target = 9
//
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]
//
//
/**
 * @author 梦境迷离
 * @time 2019-08-14
 * @version v2.0
 */
object Leetcode_1_Array {

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    import scala.collection.mutable
    val hash = mutable.HashMap[Int, Int]()
    val indexs = new Array[Int](2)
    for (i <- nums.indices) {
      if (hash.contains(nums(i))) {
        indexs(0) = hash(nums(i))
        indexs(1) = i
        indexs
      } else hash += (target - nums(i) -> i)
    }
    indexs
  }
}
