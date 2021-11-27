/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import java.util

/**
 * 3. 无重复字符的最长子串
 *
 * @author 梦境迷离
 * @since 2021/11/26
 * @version 1.0
 */
object Leetcode_3 extends App {

  val ret = lengthOfLongestSubstring("abcabcbb")
  assert(ret == 3)

  /**
   * 484 ms,484 ms
   * 51 MB,65.15%
   *
   * @param s
   * @return
   */
  def lengthOfLongestSubstring(s: String): Int = {
    // 哈希集合，记录每个字符是否出现过
    val occ = new util.HashSet[Char]()
    val n = s.length
    // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
    var rk = -1
    var ans = 0
    // i是左指针
    for (i <- 0 until n) {
      if (i != 0) { // 左指针向右移动一格，移除一个字符
        occ.remove(s.charAt(i - 1))
      }
      while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) { // 不断地移动右指针
        occ.add(s.charAt(rk + 1))
        rk += 1
      }
      // 第 i 到 rk 个字符是一个极长的无重复字符子串
      ans = math.max(ans, rk - i + 1)
    }
    ans
  }
}
