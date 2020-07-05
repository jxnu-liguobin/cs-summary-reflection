/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 965. 单值二叉树
  *
  * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
  *
  * 只有给定的树是单值二叉树时，才返回 true；否则返回 false。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-21
  * @version v1.0
  */
object Leetcode_965 extends App {

  val ret = isUnivalTree(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 记录当前节点的前缀即可
    * 540 ms,100.00%
    * 50.8 MB,100.00%
    *
    * @param root
    * @return
    */
  def isUnivalTree(root: TreeNode): Boolean = {
    var ret = true
    var preNode: TreeNode = null

    def helper(r: TreeNode): Unit = {
      if (r == null) return
      helper(r.left)
      if (preNode != null && preNode.value != r.value) {
        ret = false
      }
      preNode = r
      helper(r.right)
    }

    helper(root)
    ret
  }

}
