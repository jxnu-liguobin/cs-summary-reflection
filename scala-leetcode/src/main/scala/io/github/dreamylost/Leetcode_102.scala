/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 102. 二叉树的层序遍历
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-30
  * @version v1.0
  */
object Leetcode_102 extends App {

  val ret = levelOrder(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 704 ms,8.57%
    * 51.6 MB,100.00%
    *
    * @param root
    * @return
    */
  def levelOrder(root: TreeNode): List[List[Int]] = {
    var ret = List[List[Int]]()
    if (root == null) return ret
    var queue = List[TreeNode]()
    queue = queue ::: List(root)
    while (queue.nonEmpty) {
      var levelValue = List.empty[Int]
      val queueSize = queue.size
      for (_ <- 0 until queueSize) {
        val node = queue.head
        queue = queue.tail
        //注意: 对于::或:::方法，a.::(b) 等价 b::a
        levelValue = levelValue ::: List(node.value)
        if (node.left != null) queue = queue ::: List(node.left)
        if (node.right != null) queue = queue ::: List(node.right)
      }
      ret = ret ::: List(levelValue)
    }
    ret
  }

}
