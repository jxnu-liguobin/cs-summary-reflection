/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
  * 841. 钥匙和房间 (Medium)
  *
  * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，
  * 并且房间里可能有一些钥匙能使你进入下一个房间。
  *
  * 在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，
  * 每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，
  * 其中 N = rooms.length。 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
  *
  * 最初，除 0 号房间外的其余所有房间都被锁住。
  *
  * 你可以自由地在房间之间来回走动。
  *
  * 如果能进入每个房间返回 true，否则返回 false。
  *
  * @see <a href="https://leetcode-cn.com/problems/keys-and-rooms/">leetcode-cn.com</a>
  */
object Leetcode_841 extends App {

  /**
    * 深度优先搜索典型题目，注意退出条件：
    *
    * - 搜索过集合长度和房间数相等时就退出
    * - 当前搜索的房间中所有钥匙都在结果集中就退出
    */
  def canVisitAllRooms(rooms: List[List[Int]]): Boolean = {
    val visited: mutable.Set[Int] = mutable.Set(0)

    def dfs(index: Int): Unit = {
      if (
        index >= rooms.length || visited.size == rooms.size || rooms(index).forall(visited.contains)
      )
        return
      // 先将所有的元素添加进访问列表中，再进行递归操作
      // 确保在拥有尽可能多的钥匙条件下去递归
      visited.++(rooms(index))
      for (i <- rooms(index)) {
        dfs(i)
      }
    }

    if (rooms.isEmpty) true
    else {
      dfs(0)
      visited.size == rooms.size
    }
  }

  assert(
    canVisitAllRooms(
      List(
        List(1),
        List(2),
        List(3),
        List()
      )
    )
  )
  assert(
    canVisitAllRooms(
      List(
        List(1, 3, 2),
        List(2, 3),
        List(2, 1, 3, 1),
        List(0)
      )
    )
  )
}
