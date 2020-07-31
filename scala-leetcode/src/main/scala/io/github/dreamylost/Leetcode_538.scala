/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 538. 把二叉搜索树转换为累加树
  *
  * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-12
  * @version v1.0
  */
object Leetcode_538 extends App {

  val ret = convertBST2(TreeNodeData.treeData3_4())
  println(ret.left.value)
  println(ret.value)
  println(ret.right.value)

  /**
    * 3292 ms,10.00%
    * 59.7 MB,100.00%
    * 函数式写法，仅供参考，很慢
    *
    * @param root
    * @return
    */
  def convertBST(root: TreeNode): TreeNode = {
    var values = Seq[Int]()

    def helper(root: TreeNode)(node: TreeNode => Unit): Unit = {
      if (root == null) return
      helper(root.left)(node)
      node(root)
      helper(root.right)(node)
    }

    //1.对于搜索树，记录中序值，大于当前节点即排在当前节点后面的值
    helper(root) { cur =>
      if (cur != null) {
        values = values ++ Seq(cur.value)
      }
    }

    //2.对于有序数组，取当前节点后面的所有节点的总和
    helper(root) { cur =>
      val gtSum = values.takeRight(values.length - values.indexOf(cur.value) - 1).sum
      cur.value = cur.value + gtSum
    }

    root
  }

  /**
    * 反过来遍历搜索树，将已遍历的节点的和加到当前节点值上
    *
    * 快一倍
    * 1740 ms,20.00%
    * 59 MB,100.00%
    *
    * @param root
    * @return
    */
  def convertBST2(root: TreeNode): TreeNode = {
    var num = 0

    def BST(r: TreeNode): Unit = {
      if (r == null) return
      BST(r.right)
      r.value = r.value + num
      num = r.value
      BST(r.left)
    }

    BST(root)
    root
  }
}
