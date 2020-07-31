/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 1022. 从根到叶的二进制数之和
  *
  * 给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
  *
  * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。以 10^9 + 7 为模，返回这些数字之和。
  * @author 梦境迷离 dreamylost
  * @since 2020-06-23
  * @version v1.0
  */
object Leetcode_1022 extends App {

  val ret = sumRootToLeaf(TreeNodeData.treeData3_6())
  val ret2 = sumRootToLeaf2(TreeNodeData.treeData3_6())
  println(ret)
  println(ret2)

  /**
    * Scala不能更新形参，dfs时只能记录到当前节点上
    *
    * 540 ms,100.00%
    * 51.5 MB,100.00%
    *
    * @param root
    * @return
    */
  def sumRootToLeaf(root: TreeNode): Int = {
    val mod = 1000000007

    def dfs(r: TreeNode): Int = {
      if (r == null) return 0
      val temp = r.value % mod
      if (r.left == null && r.right == null) {
        return temp
      }
      if (r.left != null) r.left.value += temp << 1
      if (r.right != null) r.right.value += temp << 1
      (dfs(r.left) + dfs(r.right)) % mod
    }

    dfs(root)
  }

  /**
    * 硬解，计算路径的所有和
    * 604 ms,25.00%
    * 51.7 MB,100.00%
    *
    * @param root
    * @return
    */
  def sumRootToLeaf2(root: TreeNode): Int = {
    val mod = 1000000007
    var result = Seq[String]()

    //s必须传值
    def helper(r: TreeNode, path: String): Unit = {
      if (r == null) return
      if (r.left == null && r.right == null) {
        result = result ++ Seq(path + r.value + "")
        return
      }
      if (r.left != null) helper(r.left, path + r.value + "")
      if (r.right != null) helper(r.right, path + r.value + "")
    }

    helper(root, new String)
    result.map(r => java.lang.Long.parseLong(r, 2) % mod).sum.asInstanceOf[Int]
  }
}
