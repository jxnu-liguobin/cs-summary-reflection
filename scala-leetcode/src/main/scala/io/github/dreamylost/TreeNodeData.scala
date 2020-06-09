package io.github.dreamylost

/**
  *
 * @author 梦境迷离 dreamylost
  * @since 2020-06-07
  * @version v1.0
  */
object TreeNodeData {

  def treeData3(): TreeNode = {
    val root = new TreeNode(2)
    val r1 = new TreeNode(3)
    val l1 = new TreeNode(1)
    root.right = r1
    r1.left = l1
    root
  }

  def treeData1(): TreeNode = {
    val root = new TreeNode(2147483647)
    root
  }

  def treeData3_1(): TreeNode = {
    val root = new TreeNode(1)
    val r1 = new TreeNode(5)
    val l1 = new TreeNode(3)
    root.right = r1
    r1.left = l1
    root
  }

  def treeData3_3(): TreeNode = {
    val root = new TreeNode(5)
    val r1 = new TreeNode(5)
    val l1 = new TreeNode(6)
    root.right = r1
    root.left = l1
    root
  }
}
