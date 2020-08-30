/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
  * 32. 最长有效括号 (Hard)
  *
  * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
  *
  * @see <a href="https://leetcode-cn.com/problems/longest-valid-parentheses/">leetcode-cn.com</a>
  */
object Leetcode_32 extends App {

  /**
    * 方法一：使用栈
    * 遇见 ')' 时要将元素出栈，如果当前栈为空，那么将当前节点下标放入栈中，
    * 这样就能保证栈底的元素一个闭括号，以此记录有效括号序列的长度。
    *
    * 需要注意的是，由于括号列表可能第一个括号就是闭括号，因此我们预先放入 -1 。
    */
  def longestValidParentheses1(s: String): Int = {
    val stack: mutable.Stack[Int] = mutable.Stack(-1)
    var res = 0
    for (i <- s.indices) {
      if (s.charAt(i) == '(')
        stack.push(i)
      else {
        stack.pop()
        if (stack.isEmpty) stack.push(i)
        res = math.max(res, i - stack.top)
      }
    }
    res
  }

  /**
    * 方法二：动态规划
    */
  def longestValidParentheses2(s: String): Int = {
    var max = 0
    // 初始化 dp ，'(' 处一定是0
    val dp: Array[Int] = Array.fill(s.length)(0)
    for (i <- dp.indices.drop(1)) {
      if (s.charAt(i) == ')') {
        if (s.charAt(i - 1) == '(') {
          // ...()...
          // 这种情况 dp 方程为：dp(i) = dp(i - 2) + 2
          // 当然要考虑初始时 i < 2 的情况
          if (i >= 2) dp(i) = dp(i - 2) + 2
          else dp(i) = 2
        } else if (i - dp(i - 1) > 0 && s.charAt(i - dp(i - 1) - 1) == '(') {
          // ......((......))......
          // 注意 dp(i) 代表的是在i处有效的子括号的长度，此时dp方程为：
          // dp(i) = dp(i - 1) + dp(i - dp(i - 1) - 2) + 2
          // 即：当前总长度就等于当前有效子串长度加上上一个连续的有效子串的长度（不在当前括号内）
          // 当前有效子串的长度（当前括号内）就等于 dp(i - 1) + 2
          if (i - dp(i - 1) >= 2)
            dp(i) = dp(i - 1) + dp(i - dp(i - 1) - 2) + 2
          else
            dp(i) = dp(i - 1) + 2
        }
        max = math.max(max, dp(i))
      }
    }
    max
  }
}
