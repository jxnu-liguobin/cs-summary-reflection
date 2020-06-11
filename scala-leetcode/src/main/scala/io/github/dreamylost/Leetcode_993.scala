package io.github.dreamylost

/**
  * 993. 二叉树的堂兄弟节点
  *
 * 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
  *
 * 如果二叉树的两个节点深度相同，但父节点不同，则它们是一对堂兄弟节点。
  *
 * 我们给出了具有唯一值的二叉树的根节点 root，以及树中两个不同节点的值 x 和 y。
  *
 * 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true。否则，返回 false。
  *
 * @author 梦境迷离
  * @version 1.0,2020/6/11
  */
object Leetcode_993 extends App {

  val ret = isCousins(TreeNodeData.treeData5_1(), 5, 4)
  println(ret)

  /**
    * 堂兄弟是同level的，使用层序遍历
    * 584 ms,100.00%
    * 53.4 MB,100.00%
    *
   * @param root
    * @param x
    * @param y
    * @return
    */
  def isCousins(root: TreeNode, x: Int, y: Int): Boolean = {
    import scala.collection.mutable
    def levelTraverse(root: TreeNode): Boolean = {
      if (root == null) return false
      val queue = mutable.Queue[TreeNode]()
      queue.enqueue(root)
      while (queue.nonEmpty) {
        var curLevel = Seq[Int]()
        val levelLength = queue.size
        for (_ <- 0 until levelLength) {
          val node = queue.dequeue
          if (node == null) {
            curLevel = curLevel ++ mutable.Seq(0)
          } else {
            curLevel = curLevel ++ mutable.Seq(node.value)
            queue.enqueue(node.left)
            queue.enqueue(node.right)
          }
          println(curLevel)
        }
        if (curLevel.contains(x) && curLevel.contains(y)) {
          val xI = curLevel.indexOf(x)
          val yI = curLevel.indexOf(y)
          if (xI > yI && xI - 1 == yI && (yI % 2 == 0)) {
            return false
          }
          if (xI < yI && xI == yI - 1 && (xI % 2 == 0)) {
            return false
          }
          return true
        }
      }
      false
    }

    levelTraverse(root)
  }
}
