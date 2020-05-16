package io.github.dreamylost

/**
  * 给定数组arr那是[0, 1, ..., arr.length - 1]，我们将数组拆分成一定数量的“块”(分区)，并对每个块进行单独排序。连接它们之后，结果等于排序数组。
  * 我们能做多少块？
  *
 * @author 梦境迷离
  * @time 2018年7月19日
  * @version v1.0
  */
object Leetcode_769_Array extends App {

  /**
    * 其基本思想是使用max[]数组跟踪最大值直到当前位置，并将其与排序数组(索引从0到arr.ength-1)进行比较。如果max[i]等于排序数组中索引i处的元素，则最后计数+。
    * 更新：正如@AF8EJFE所指出的，数字从0到arr.ength-1不等。因此，没有必要对ARR进行排序，我们可以简单地使用索引进行比较。
    */
  val ret = maxChunksToSorted(Array(4, 3, 2, 1, 0))
  print(ret)

  def maxChunksToSorted(arr: Array[Int]): Int = {
    if (arr == null || arr.length == 0) return 0
    var count = 0
    var max = 0
    for (i <- 0 until arr.length) {
      max = Math.max(max, arr(i))
      if (max == i) {
        count += 1
      }
    }
    count
  }

}
