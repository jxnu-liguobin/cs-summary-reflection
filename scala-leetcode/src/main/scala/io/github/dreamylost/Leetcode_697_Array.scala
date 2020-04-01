package io.github.dreamylost

import scala.collection.mutable.HashMap

/**
 * 数组的度
 *
 * 697. Degree of an Array (Easy)
 *
 * Input: [1,2,2,3,1,4,2]
 * Output: 6
 * 题目描述：数组的度定义为元素出现的最高频率，例如上面的数组度为 3。
 * 要求找到一个最小的子数组，这个子数组的度和原数组一样。
 *
 * @author 梦境迷离
 * @time 2018年7月20日
 * @version v1.0
 */
object Leetcode_697_Array extends App {

  print(findShortestSubArray(Array(1, 2, 2, 3, 1, 4, 2)))

  def findShortestSubArray(nums: Array[Int]): Int = {
    val map = new HashMap[Integer, Array[Int]]
    //record分别记录[最大频率，最小索引，最大索引]
    var record: Array[Int] = null
    var maxFreq = 0
    for (i <- 0 until nums.length) {
      if (!map.contains(nums(i))) {
        map.put(nums(i), Array(1, i, i))
      } else {
        record = map.get(nums(i)).get
        map.put(nums(i), Array(record(0) + 1, record(1), i))
      }
      //更新最大频率
      record = map.get(nums(i)).get
      maxFreq = math.max(maxFreq, record(0))
    }
    var minLen = Int.MaxValue
    for (n <- nums) {
      record = map.get(n).get
      if (record(0) == maxFreq) {
        if (record(2) - record(1) < minLen) {
          minLen = record(2) - record(1) + 1
        }
      }
    }

    minLen
  }

}