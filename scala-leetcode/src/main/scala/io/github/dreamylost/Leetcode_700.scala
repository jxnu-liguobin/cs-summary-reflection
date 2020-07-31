/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 700. 二叉搜索树中的搜索
  *
  * 给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-17
  * @version v1.0
  */
object Leetcode_700 extends App {

  val ret = searchBST(TreeNodeData.treeData3_5(), 1)
  println(ret)

  /**
    * 836 ms,50.00%
    * 56.2 MB,100.00%
    *
    * @param root
    * @param `val`
    * @return
    */
  def searchBST(root: TreeNode, `val`: Int): TreeNode = {
    if (root == null) return null
    if (root.value == `val`) root
    else if (root.value < `val`) searchBST(root.right, `val`)
    else searchBST(root.left, `val`)
  }

}
