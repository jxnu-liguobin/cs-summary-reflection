/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 1339. 分裂二叉树的最大乘积
  *
  * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
  *
  * 由于答案可能会很大，请你将结果对 [[10^9 + 7]] 取模后再返回
  *
  * @see https://github.com/jxnu-liguobin
  * @author 梦境迷离 dreamylost
  * @version 1.0,2020/7/14
  */
object Leetcode_1339 extends App {

  val ret = maxProduct(TreeNodeData.treeData6_1())
  println(ret)

  /**
    * 2592 ms,100.00%
    * 71.5 MB,100.00%
    *
    * @param root
    * @return
    */
  def maxProduct(root: TreeNode): Int = {
    //设，以节点N与上层的边为待删除的边，乘积 = f(N上) * f(N下)，分别为N上，与N下，其中N下的子树是以N为root节点的，另外一半子树比较麻烦，可以使用 sum - f(N下)
    //即f(N上) = sum - f(N下)
    //所以子树乘积　＝　sum - f(N下)　×  f(N下)
    //其中，f(N下)比较好算，就是以当前节点为root节点的子树和，而sum更好算，就是整个树的和
    //ret = max(ret, sum - f(N下) * f(N下))

    if (root == null) return 0
    var ret = Long.MinValue

    def sum(r: TreeNode): Int = {
      if (r == null) return 0
      r.value + sum(r.left) + sum(r.right)
    }

    val allNodeSum = sum(root)

    //必须Long
    def dfs(r: TreeNode): Long = {
      if (r == null) return 0
      val cNodeSum = dfs(r.left) + dfs(r.right) + r.value
      ret = math.max(ret, (allNodeSum - cNodeSum) * cNodeSum)
      cNodeSum
    }

    dfs(root)

    (ret % (1e9 + 7)).toInt
  }
}
