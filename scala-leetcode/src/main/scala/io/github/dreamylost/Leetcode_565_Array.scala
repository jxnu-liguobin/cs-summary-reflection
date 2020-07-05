/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 嵌套数组
  *
  * 565. Array Nesting (Medium)
  *
  * Input: A = [5,4,0,3,1,6,2]
  * Output: 4
  * Explanation:
  * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
  *
  * One of the longest S[K]:
  * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
  * 题目描述：S[i] 表示一个集合，集合的第一个元素是 A[i]，第二个元素是 A[A[i]]，如此嵌套下去。求最大的 S[i]。
  *
  * @author 梦境迷离
  * @time 2018年7月19日
  * @version v1.0
  */
object Leetcode_565_Array extends App {

  print(arrayNesting2(Array(5, 4, 0, 3, 1, 6, 2)))

  def arrayNesting(nums: Array[Int]): Int = {
    var max = 0
    for (i <- 0 until nums.length) {
      var cnt = 0
      var j = i
      while ((nums(j) != -1)) {
        cnt += 1
        val t = nums(j)
        // 标记该位置已经被访问
        nums(j) = -1
        j = t
      }
      max = math.max(max, cnt)
    }
    return max
  }

  /**
    * 使用dfs
    */
  def arrayNesting2(nums: Array[Int]): Int = {
    if (nums == null || nums.length == 0) return 0
    val cArr = new Array[Int](nums.length)
    val visited = new Array[Boolean](nums.length)
    var max = 0
    for (i <- 0 until nums.length) {
      dfs(i, nums, cArr, visited)
      max = Math.max(max, cArr(i))
    }
    return max
  }

  def dfs(i: Int, nums: Array[Int], cArr: Array[Int], visited: Array[Boolean]) {
    if (cArr(i) > 0 || visited(i)) return
    var next = nums(i)
    if (cArr(next) > 0) {
      cArr(i) = cArr(next) + 1
      return
    }
    visited(i) = true
    dfs(next, nums, cArr, visited)
    cArr(i) = cArr(next) + 1
    visited(i) = false
  }

}
