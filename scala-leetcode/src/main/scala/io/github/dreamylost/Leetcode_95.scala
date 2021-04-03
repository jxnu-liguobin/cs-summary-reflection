/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 95. 不同的二叉搜索树 II
 *
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
 *
 * @author 梦境迷离 dreamylost
 * @since 2020-06-27
 * @version v1.0
 */
object Leetcode_95 extends App {

  val ret = generateTrees(3)
  ret.foreach(println)

  /**
   * 设 f(left, right) 代表所有节点范围为 left 到 right 的所有二叉搜索树，则考虑如下情况：
   * 1、left > right：将 null 压入 res 并返回
   * 2、left == right：此时符合的 BST 有且仅有一棵，将其压入 res 后返回
   * 3、left < right：枚举 [left, right] 中的所有值 root 作为根节点，其左子树应为 l_tree = f(left, root - 1)，右子树应为 r_tree = f(root + 1, right)，
   * 分别从 l_tree 和 r_tree 中取出对应的子树，和 root 连成一棵新树，并压入 res 中。循环结束后返回 res 即可
   *
   * 616 ms,25.00%
   * 50.2 MB,100.00%
   *
   * @param n
   * @return
   */
  def generateTrees(n: Int): List[TreeNode] = {
    def buildSearchTree(start: Int, end: Int): List[TreeNode] = {
      var res = List[TreeNode]()
      if (start > end) {
        res = res :+ null
        res
      } else {
        (start to end).foreach { i =>
          val l_trees = buildSearchTree(start, i - 1)
          val r_trees = buildSearchTree(i + 1, end)
          for (l <- l_trees) {
            for (r <- r_trees) {
              val root = new TreeNode(i)
              root.left = l
              root.right = r
              res = res :+ root
            }
          }

        }
        res
      }
    }
    if (n < 1) return Nil
    buildSearchTree(1, n)
  }

}
