/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 给定二叉树，按垂序遍历返回其结点值。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-09
  * @version v1.0
  */
object Leetcode_987 extends App {

  val ret = verticalTraversal(TreeNodeData.treeData11())
  println(ret)

  /**
    * 616 ms,100.00%
    * 51.2 MB,100.00%
    *
    * 1.计算坐标
    * 2.排序
    *
    * @param root
    * @return
    */
  def verticalTraversal(root: TreeNode): List[List[Int]] = {
    var locations = List.empty[Location]
    class Location(var x: Int, var y: Int, var value: Int)
    //先序计算坐标
    def dfs(node: TreeNode, x: Int, y: Int): Unit = {
      if (node == null) return
      locations = locations ::: List(new Location(x, y, node.value))
      dfs(node.left, x - 1, y + 1)
      dfs(node.right, x + 1, y + 1)
    }

    dfs(root, 0, 0)
    implicit val order = new Ordering[Location] {
      override def compare(ts: Location, tt: Location): Int = {
        //比较x,y,value
        if (ts.x != tt.x) Integer.compare(ts.x, tt.x)
        else if (ts.y != tt.y) Integer.compare(ts.y, tt.y)
        else Integer.compare(ts.value, tt.value)
      }

    }
    locations = locations.sorted
    var ret = List[List[Int]](List())
    var prev = locations.head.x
    for (loc <- locations) {
      if (loc.x != prev) {
        prev = loc.x
        ret = ret ::: List(List())
      }
      if (ret.nonEmpty) {
        if (ret.size == 1) {
          val tail: List[Int] = ret.head
          ret = List(tail ::: List(loc.value))
        } else {
          val tail: List[List[Int]] = ret.drop(ret.size - 1)
          val init: List[List[Int]] = ret.init
          ret = init ::: List(tail.head ::: List(loc.value))
        }
      }
    }
    ret
  }

}
