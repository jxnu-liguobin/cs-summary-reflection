package io.github.dreamylost

/**
 * 783. 二叉搜索树节点最小距离
 *
 * 给定一个二叉搜索树的根节点 root，返回树中任意两节点的差的最小值。
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/6/18
 */
object Leetcode_783 extends App {

  val ret = minDiffInBST(TreeNodeData.treeData3_5())
  println(ret)

  /**
   * 利用二叉搜索树特性
   *
   * 536 ms,80.00%
   * 50.7 MB,100.00%
   *
   * @param root
   * @return
   */
  def minDiffInBST(root: TreeNode): Int = {
    var preValue: TreeNode = null
    var ret = Int.MaxValue

    def bst(r: TreeNode): Unit = {
      if (r == null) return
      bst(r.left)
      if (preValue != null) {
        ret = math.min(ret, r.value - preValue.value)
      }
      preValue = r
      bst(r.right)
    }

    bst(root)
    ret
  }

}
