/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 98. 验证二叉搜索树
  *
  * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
  *
  * 假设一个二叉搜索树具有如下特征：
  *
  * 节点的左子树只包含小于当前节点的数。
  * 节点的右子树只包含大于当前节点的数。
  * 所有左子树和右子树自身必须也是二叉搜索树。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-29
  * @version v1.0
  */
object Leetcode_98 extends App {

  val ret = isValidBST(TreeNodeData.treeData3_5())
  val ret2 = isValidBST2(TreeNodeData.treeData3_5())
  println(ret)
  println(ret2)

  /**
    * 中序遍历：当前节点大于前面已经遍历的节点
    *
    * 688 ms,46.15%
    * 52.4 MB,100.00%
    *
    * @param root
    * @return
    */
  def isValidBST(root: TreeNode): Boolean = {
    //也可以记录中序遍历的所有元素再判断
    var cur = Long.MinValue

    def helper(root: TreeNode): Boolean = {
      if (root == null) return true
      val left = helper(root.left)
      if (root.value <= cur) return false
      cur = root.value
      val right = helper(root.right)
      left && right
    }

    helper(root)
  }

  /**
    * 652 ms,100.00%
    * 52.2 MB,100.00%
    *
    * @param root
    * @return
    */
  def isValidBST2(root: TreeNode): Boolean = {
    //传递值的范围区间
    def helper(root: TreeNode, min: Long, max: Long): Boolean = {
      if (root == null) return true
      if (root.value < min || root.value > max) return false
      //左子树全部小于当前节点，右子树全部大于当前节点
      helper(root.left, min, root.value) && helper(root.right, root.value, max)
    }

    helper(root, Long.MinValue, Long.MaxValue)
  }
}
