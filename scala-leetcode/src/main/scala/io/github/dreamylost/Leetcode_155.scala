/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) —— 将元素 x 推入栈中。
 * pop()—— 删除栈顶的元素。
 * top()—— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *
 * @author 梦境迷离
 * @version 1.0,2021/2/13
 */
object Leetcode_155 {

  //1272 ms,8.33% 用 ::: 和 last 会比较慢：Time: List has O(1) prepend and head/tail access
  //62.9 MB,8.33%

  //804 ms,100.00%
  //60.7 MB,41.67%
  class MinStack() {

    /** initialize your data structure here. */
    var data: List[Int] = List[Int]()
    var min: Int = Int.MaxValue

    def push(x: Int) {
      //栈顶第一个为最小数据，第二个为次小值
      if (x <= min) {
        data = min :: data
        min = x
      }
      data = x :: data
    }

    def pop() {
      val top = data.head
      data = data.tail
      if (top == min) {
        min = data.head
        data = data.tail
      }
    }

    def top(): Int = {
      data.head
    }

    def getMin(): Int = {
      min
    }

  }

  /**
   * Your MinStack object will be instantiated and called as such:
   * var obj = new MinStack()
   * obj.push(x)
   * obj.pop()
   * var param_3 = obj.top()
   * var param_4 = obj.getMin()
   */

}
