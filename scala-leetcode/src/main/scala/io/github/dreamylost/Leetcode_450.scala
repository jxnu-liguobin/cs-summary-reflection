/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 450. 删除二叉搜索树中的节点
  *
  * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
  *
  * @see https://github.com/jxnu-liguobin
  * @author 梦境迷离
  * @since 2020-08-02
  * @version 1.0
  */
object Leetcode_450 {

  /**
    * 2380 ms,100.00%
    * 58.8 MB,100.00%
    *
    * @param root
    * @param key
    * @return
    */
  def deleteNode(root: TreeNode, key: Int): TreeNode = {
    if (root == null) return null
    if (key > root.value) {
      root.right = deleteNode(root.right, key)
    } else if (key < root.value) {
      root.left = deleteNode(root.left, key)
    } else {
      if (root.left == null) return root.right
      if (root.right == null) return root.left
      //说明两个子节点都不为空，我们可以找左子树的最大值，也可以找右子树的最小值替换
      val maxNode: TreeNode = findMax(root.left)
      root.value = maxNode.value
      root.left = deleteNode(root.left, root.value)
    }
    root
  }

  /**
    * 找到左子树中的最大值，即当前节点的中序遍历前驱
    * 1.当左子树没有右节点时，最大值就是左节点本身
    * 2.当左子树有右节点时，最大值就是最最右边的节点
    *
    * @param root
    * @return
    */
  private def findMax(root: TreeNode): TreeNode = {
    var node = root
    while (node.right != null) node = node.right
    node
  }
}
