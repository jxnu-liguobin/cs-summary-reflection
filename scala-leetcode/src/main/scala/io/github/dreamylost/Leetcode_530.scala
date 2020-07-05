/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 530. 二叉搜索树的最小绝对差
  *
  * 给你一棵所有节点为非负值的二叉搜索树，请你计算树中任意两节点的差的绝对值的最小值。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-08
  * @version v1.0
  */
object Leetcode_530 extends App {

  val ret = getMinimumDifference(TreeNodeData.treeData3_1())
  println(ret)

  /**
    * 772 ms,40.00%
    * 52.9 MB,100.00%
    * 的用户
    *
    * @param root
    * @return
    */
  def getMinimumDifference(root: TreeNode): Int = {
    if (root == null) return 0
    var min = Int.MaxValue
    var pre = -1

    //中序的有序遍历，比较当前节点与前一个节点
    def inOrder(root: TreeNode): Unit = {
      if (root == null) return
      inOrder(root.left)
      if (pre != -1) {
        min = Math.min(Math.abs(root.value - pre), min)
      }
      pre = root.value
      inOrder(root.right)
    }

    inOrder(root)
    min
  }

}
