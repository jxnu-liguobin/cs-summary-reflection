package io.github.dreamylost

/**
  * 669. 修剪二叉搜索树
  *
 * 给定一个二叉搜索树，同时给定最小边界L 和最大边界 R。通过修剪二叉搜索树，使得所有节点的值在[L, R]中 (R>=L) 。
  * 你可能需要改变树的根节点，所以结果应当返回修剪好的二叉搜索树的新的根节点。
  *
 * @author 梦境迷离 dreamylost
  * @since 2020-06-16
  * @version v1.0
  */
object Leetcode_669 extends App {

  val ret = trimBST(TreeNodeData.treeData3_5(), 1, 2)
  println(ret)

  /**
    * 修剪BST树，其步骤为：
    * 若为空树，返回NULL;
    * 否则：
    *       1.先修剪根，若根的值不在[L,R]范围内，则执行如下循环：
    * 若根小于下限L，必然有其左子树结点全部小于L，放弃根和左子树，使右子树的根成为新树的根。
    * 大于上限R的情况同理。最后结束时，要么根为空，要么根的值在[L,R]中。
    *       2.递归修剪左子树。
    *       3.递归修建右子树。
    *
   * @param root
    * @param L
    * @param R
    * @return
    */
  def trimBST(root: TreeNode, L: Int, R: Int): TreeNode = {
    if (root == null) return null
    if (root.value < L) {
      return trimBST(root.right, L, R)
    }
    if (root.value > R) {
      return trimBST(root.left, L, R)
    }

    root.left = trimBST(root.left, L, R)
    root.right = trimBST(root.right, L, R)
    root
  }
}
