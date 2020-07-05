/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 897. 递增顺序查找树
  *
  * 给你一个树，请你 按中序遍历 重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-20
  * @version v1.0
  */
object Leetcode_897 extends App {

  val ret = increasingBST(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 暴力
    * 544 ms,100.00%
    * 50.9 MB,100.00%
    *
    * @param root
    * @return
    */
  def increasingBST(root: TreeNode): TreeNode = {
    var values = Seq[Int]()

    def inOrder(r: TreeNode): Unit = {
      if (r == null) return
      inOrder(r.left)
      values = values ++ Seq(r.value)
      inOrder(r.right)
    }

    inOrder(root)
    val ret = new TreeNode(0)
    var h = ret
    values.foreach { v =>
      h.right = new TreeNode(v)
      h = h.right
    }
    ret.right
  }

  /**
    * 中序记录前缀节点
    * 544 ms,100.00%
    * 50.7 MB,100.00%
    *
    * @param root
    * @return
    */
  def increasingBST2(root: TreeNode): TreeNode = {
    var preNode: TreeNode = null

    def inOrder(node: TreeNode) {
      if (node == null) return
      inOrder(node.left)
      //断掉当前节点的左子树节点，并将当前节点链接到前缀节点的右子树
      node.left = null
      preNode.right = node
      preNode = node
      inOrder(node.right)
    }

    val ret = new TreeNode(0)
    preNode = ret
    inOrder(root)
    ret.right
  }
}
