package io.github.dreamylost

/**
  * 872. 叶子相似的树
  *
  * 请考虑一颗二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个叶值序列 。
  * 举个例子，如上图所示，给定一颗叶值序列为 (6, 7, 4, 9, 8) 的树。
  *
  * 如果有两颗二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
  *
  * 如果给定的两个头结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-20
  * @version v1.0
  */
object Leetcode_872 extends App {

  val ret = leafSimilar(TreeNodeData.treeData3_5(), TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 592 ms,100.00%
    * 50.7 MB,100.00%
    *
    * @param root1
    * @param root2
    * @return
    */
  def leafSimilar(root1: TreeNode, root2: TreeNode): Boolean = {
    import scala.collection.mutable.ListBuffer
    def getLeaf(root: TreeNode, r: ListBuffer[Int]): Unit = {
      if (root == null) return
      if (root.left == null && root.right == null) {
        r.append(root.value)
      }
      root.value
      getLeaf(root.left, r)
      getLeaf(root.right, r)
    }

    val t1 = ListBuffer[Int]()
    val t2 = ListBuffer[Int]()
    getLeaf(root1, t1)
    getLeaf(root2, t2)
    println(t1)
    println(t2)
    t1 == t2
  }
}
