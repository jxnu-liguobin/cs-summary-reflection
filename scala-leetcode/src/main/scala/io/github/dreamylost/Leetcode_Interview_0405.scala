package io.github.dreamylost

/**
  * 面试题 04.05. 合法二叉搜索树
  *
  * 实现一个函数，检查一棵二叉树是否为二叉搜索树。
  *
  * @author 梦境迷离 dreamylost
  * @version 1.0,2020/7/3
  */
object Leetcode_Interview_0405 extends App {

  val ret = isValidBST(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 与LeetCode98一模一样但是存在重复元素，多个相等判断
    *
    * 680 ms,80.00%
    * 52.4 MB,100.00%
    *
    * @param root
    * @return
    */
  def isValidBST(root: TreeNode): Boolean = {

    def helper(root: TreeNode, min: Long, max: Long): Boolean = {
      if (root == null) return true
      if (root.value <= min || root.value >= max) return false
      helper(root.left, min, root.value) && helper(root.right, root.value, max)
    }

    helper(root, Long.MinValue, Long.MaxValue)

  }
}
