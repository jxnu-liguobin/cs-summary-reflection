/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 找出二叉树中第二小的节点
  *
  * 671. Second Minimum Node In a Binary Tree (Easy)
  * 给定一个非空的特殊二叉树，由具有非负值的节点组成，其中该树中的每个节点都有两个或零个子节点。如果节点有两个子节点，那么该节点的值是其两个子节点之间的较小值。
  * 如果不存在这样的第二最小值，则输出-1代替。
  *
  * @author 梦境迷离 https://github.com/jxnu-liguobin
  * @time 2018年8月10日
  * @version v1.0
  */
object Leetcode_671_Tree extends App {

  /**
    * 如果节点n的值大于min，则以n为子树的节点值至少大于等于min，故以n为子树的节点不存在第二小值
    * 528 ms,80.00%
    * 50.3 MB,100.00%
    *
    * @param root
    * @return
    */
  def findSecondMinimumValue(root: TreeNode): Int = {
    if (root == null || (root.left == null && root.right == null)) return -1
    var leftVal = root.left.value
    var rightVal = root.right.value
    //找出候选数，默认就是子节点值，如果子节点值和root值相同，递归，在子树中寻找候选数
    if (leftVal == root.value) leftVal = findSecondMinimumValue(root.left)
    if (rightVal == root.value) rightVal = findSecondMinimumValue(root.right)
    //如果左右候选数都正常，返回较小值就可(左右都至少大于等于其根，所以根是最小的)
    if (leftVal != -1 && rightVal != -1) return Math.min(leftVal, rightVal)
    if (leftVal != -1) leftVal else rightVal
  }

  val ret = findSecondMinimumValue2(TreeNodeData.treeData3_3())
  println(ret)

  /**
    * 暴力
    * 532 ms,60.00%
    * 50.7 MB,100.00%
    *
    * @param root
    * @return
    */
  def findSecondMinimumValue2(root: TreeNode): Int = {
    import scala.collection.SortedSet
    var values = Seq[Int]()

    def helper(root: TreeNode): Unit = {
      if (root == null) return
      values = values ++ Seq(root.value)
      helper(root.left)
      helper(root.right)
    }

    helper(root)
    val sortedSet = SortedSet(values: _*)
    if (values.size < 2 || sortedSet.size < 2) -1 else sortedSet.tail.head
  }
}
