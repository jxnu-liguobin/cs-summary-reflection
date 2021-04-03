/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 子树
 *
 * 572. Subtree of Another Tree (Easy)
 *
 * Given tree s:
 *     3
 *    / \
 *   4   5
 *  / \
 * 1   2
 *
 * Given tree t:
 *   4
 *  / \
 * 1   2
 *
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 * Given tree s:
 *
 *     3
 *    / \
 *   4   5
 *  / \
 * 1   2
 *    /
 *   0
 *
 * Given tree t:
 *   4
 *  / \
 * 1   2
 *
 * Return false
 * @author 梦境迷离
 * @time 2018年7月18日
 * @version v1.0
 */
object Leetcode_572_Tree extends App {

  def isSubtree(s: TreeNode, t: TreeNode): Boolean = {
    if (t == null) return true
    if (s == null) return false
    isSubtreeWithRoot(s, t) || isSubtreeWithRoot(s.left, t) || isSubtreeWithRoot(s.right, t)
  }

  private def isSubtreeWithRoot(s: TreeNode, t: TreeNode): Boolean = {
    if (s == null && t == null) return true
    if (s == null || t == null) return false
    s.value == t.value && isSubtreeWithRoot(s.left, t.left) && isSubtreeWithRoot(s.right, t.right)
  }

}
