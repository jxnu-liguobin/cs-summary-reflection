/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 979. 在二叉树中分配硬币
  *
  * 给定一个有 N 个结点的二叉树的根结点 root，树中的每个结点上都对应有 node.val 枚硬币，并且总共有 N 枚硬币。
  *
  * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。(移动可以是从父结点到子结点，或者从子结点移动到父结点。)。
  *
  * 返回使每个结点上只有一枚硬币所需的移动次数。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-07
  * @version v1.0
  */
object Leetcode_979 extends App {

  val ret = distributeCoins(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 父节点移动的数量就是子节点缺失和多余的总和，也等同于步数。
    *
    * 与 最长同值路径相似
    *
    * 588 ms,100.00%
    * 51 MB,100.00%
    *
    * @param root
    * @return
    */
  def distributeCoins(root: TreeNode): Int = {
    var ret = 0

    def helper(r: TreeNode): Int = {
      if (r == null) return 0
      if (r.left != null) {
        r.value += helper(r.left)
      }
      //判断会快点，少一次调用helper
      if (r.right != null) {
        r.value += helper(r.right)
      }
      ret += math.abs(r.value - 1)
      //当前节点需要移动的步数，可能为负值
      //后续遍历最终访问root，此时root上的value的绝对值就是一个需要移动的补数
      r.value - 1
    }

    helper(root)
    ret
  }

  /**
    * 592 ms,100.00%
    * 51.1 MB,100.00%
    *
    * @param root
    * @return
    */
  def distributeCoins2(root: TreeNode): Int = {
    var ret = 0

    //还是后续遍历，但是每次计算的是子节点的步数
    def helper(r: TreeNode): Int = {
      if (r == null) return 0
      val left = helper(r.left)
      val right = helper(r.right)
      ret += math.abs(left) + math.abs(right)
      left + right + r.value - 1
    }

    helper(root)
    ret
  }
}
