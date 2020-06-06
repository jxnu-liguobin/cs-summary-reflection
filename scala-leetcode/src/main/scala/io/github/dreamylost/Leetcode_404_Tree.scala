package io.github.dreamylost

/**
  * 统计左叶子节点的和
  *
  * 404. Sum of Left Leaves (Easy)
  *
  * 3
  * / \
  * 9  20
  * /  \
  * 15   7
  *
  * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
  *  @author 梦境迷离
  *  @time 2018年8月9日
  *  @version v1.0
  */
object Leetcode_404_Tree extends App {

  //564 ms,66.67%
  //50.8 MB,100.00%
  def sumOfLeftLeaves(root: TreeNode): Int = {
    Option(root).fold(0) { root =>
      val isLeaf =
        if (root.left == null) false
        else root.left.left == null && root.left.right == null
      if (isLeaf) root.left.value + sumOfLeftLeaves(root.right)
      else sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right)
    }
  }
}
