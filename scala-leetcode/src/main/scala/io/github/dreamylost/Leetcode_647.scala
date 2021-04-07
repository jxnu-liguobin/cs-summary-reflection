/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 647. 回文子串
 *
 * @author 梦境迷离
 * @since 2021/4/7
 * @version 1.0
 */
object Leetcode_647 extends App {
  object Solution {

    /**
     * 中心扩散
     * 524 ms,100.00%
     * 50.2 MB,100.00%
     */
    def countSubstrings(s: String): Int = {
      var n = 0
      for (i <- 0 until s.length) {
        count(i, i)
        count(i, i + 1)
      }

      def count(start: Int, end: Int): Unit = {
        var sdx = start
        var edx = end
        while (sdx >= 0 && edx < s.length && s.charAt(sdx) == s.charAt(edx)) {
          sdx -= 1
          edx += 1
          n += 1
        }
      }

      n
    }

    /**
     * DP520 ms,100.00%
     * 50.3 MB,66.67%
     */
    def countSubstrings2(s: String): Int = {
      // dp[i][j] 表示[i,j]的字符是否为回文子串
      val dp = Array[Array[Boolean]]((0 until s.length).map(_ => new Array[Boolean](s.length)): _*)
      // 因为要求dp[i][j] 需要知道dp[i+1][j-1]
      var ans = 0
      for (i <- s.length - 1 to 0 by -1) {
        var j = i
        while (j <= s.length - 1) {
          if ((s.charAt(i) == s.charAt(j)) && (j - i <= 1 || dp(i + 1)(j - 1))) {
            dp(i)(j) = true
            ans += 1
          }
          j += 1
        }
      }
      ans
    }
  }

  val res = Solution.countSubstrings("abc")
  val res2 = Solution.countSubstrings2("abc")
  println(res)
  println(res2)

}
