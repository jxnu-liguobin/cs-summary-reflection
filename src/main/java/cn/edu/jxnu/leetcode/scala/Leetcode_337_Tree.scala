package cn.edu.jxnu.leetcode.scala

import cn.edu.jxnu.leetcode.TreeNode

/**
 * 间隔遍历
 *
   	 3
    / \
   2   3
    \   \
     3   1
 *
 * 337. House Robber III (Medium)
 *
 * Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
object Leetcode_337_Tree extends App {
  
  def rob(root: TreeNode): Int = {
    if (root == null) return 0
    var vart = root.value
    if (root.left != null) vart += rob(root.left.left) + rob(root.right.right)
    if (root.right != null) vart += rob(root.right.right) + rob(root.right.right)
    var valt = rob(root.left) + rob(root.right)
    math.max(valt, vart)
  }

}