/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 129. 求根到叶子节点数字之和
  *
  * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
  *
  * 例如，从根到叶子节点路径 1->2->3 代表数字 123。
  *
  * 计算从根到叶子节点生成的所有数字之和。
  *
  * 说明: 叶子节点是指没有子节点的节点。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-16
  * @version v1.0
  */
object Leetcode_129 extends App {

  val ret = sumNumbers(TreeNodeData.treeData5_1())
  println(ret)

  /**
    * 620 ms,100.00%
    * 51.2 MB,100.00%
    * 最优解是：利用进制
    *
    * @see Leetcode_1022
    * @param root
    * @return
    */
  def sumNumbers(root: TreeNode): Int = {
    var ret = Seq.empty[String]

    def helper(r: TreeNode, path: String): Unit = {
      if (r == null) return
      if (r.left == null && r.right == null) {
        ret = ret ++ Seq(path + r.value.toString)
      }
      helper(r.left, path + r.value.toString)
      helper(r.right, path + r.value.toString)
    }

    helper(root, new String)
    ret.map(p => Integer.parseInt(p)).sum
  }
}
