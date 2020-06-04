package io.github.dreamylost

/**
  * 235. 二叉搜索树的最近公共祖先
  * @author 梦境迷离
  * @since 2020-06-04
  * @version v1.0
  */
object Leetcode_235 extends App {

  /**
    * 868 ms,88.89%
    * 57.8 MB,100.00%
    * @param root
    * @param p
    * @param q
    * @return
    */
  def lowestCommonAncestor(root: TreeNode, p: TreeNode, q: TreeNode): TreeNode = {
    //利用二叉搜索树特性
    Option(root).fold(root) { root =>
      if (root.value > q.value && root.value > p.value) {
        lowestCommonAncestor(root.left, p, q)
      } else if (root.value < q.value && root.value < p.value) {
        lowestCommonAncestor(root.right, p, q)
      } else {
        root
      }
    }

  }

}
