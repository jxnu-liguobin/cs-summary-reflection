/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 222. 完全二叉树的节点个数
 * 给出一个完全二叉树，求出该树的节点个数。
 *
 * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
 * 若最底层为第 h 层，则该层包含 1~ 2h 个节点。
 *
 * @see https://github.com/jxnu-liguobin
 * @author dreamylost
 * @since 2020-07-24
 * @version v1.0
 */
object Leetcode_222 extends App {

  val ret = countNodes(TreeNodeData.treeData10())
  println(ret)

  val ret2 = countNodes2(TreeNodeData.treeData10())
  println(ret2)

  /**
   * 暴力
   * 1164 ms,75.00%
   * 56.6 MB,100.00%
   *
   * @param root
   * @return
   */
  def countNodes(root: TreeNode): Int = {
    if (root == null) 0 else countNodes(root.left) + countNodes(root.right) + 1
  }

  /**
   * 每层有 2^(K-1) 个，总共有 2^k - 1个
   *
   * 1120 ms,100.00%
   * 56.4 MB,100.00%
   *
   * @param root
   * @return
   */
  def countNodes2(root: TreeNode): Int = {
    if (root == null) {
      return 0
    }
    val left = countLevel(root.left)
    val right = countLevel(root.right)
    //left == right，左子树是满二叉树；left !=right，右子树是满二叉树
    //这里是递归到左子树的时候，判断若当前节点的左右层数
    //左子树总共有 left=2^left - 1 加上当前的root刚好是2^left，再加上right即可
    if (left == right) {
      countNodes2(root.right) + (1 << left)
    } else {
      //当不相等时，左子树有单独的叶子，但此时右子树的满的，右子树同理可以直接计算
      countNodes2(root.left) + (1 << right)
    }
  }

  private[this] def countLevel(root: TreeNode): Int = {
    if (root == null) 0 else math.max(countLevel(root.left), countLevel(root.right)) + 1
  }

}
