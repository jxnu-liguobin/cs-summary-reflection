/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 103. 二叉树的锯齿形层次遍历
  *
  * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-01
  * @version v1.0
  */
object Leetcode_103 extends App {

  val ret = zigzagLevelOrder(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 记录层数，偶数层时翻转当前层的值或者记录当前值时就根据奇偶使用头插法/尾插法
    * 632 ms,20.00%
    * 51.4 MB,100.00%
    *
    * @param root
    * @return
    */
  def zigzagLevelOrder(root: TreeNode): List[List[Int]] = {
    var level = 1
    var ret = List[List[Int]]()
    if (root == null) return ret
    var queue = List[TreeNode]()
    queue = queue ::: List(root)
    while (queue.nonEmpty) {
      var levelValues = List.empty[Int]
      val queueSize = queue.size
      for (_ <- 0 until queueSize) {
        val node = queue.head
        queue = queue.tail
        levelValues =
          if (level % 2 == 1) levelValues ::: List(node.value) else List(node.value) ::: levelValues
        if (node.left != null) queue = queue ::: List(node.left)
        if (node.right != null) queue = queue ::: List(node.right)
      }
      ret = ret ::: List(levelValues)
      level += 1
    }
    ret
  }

}
