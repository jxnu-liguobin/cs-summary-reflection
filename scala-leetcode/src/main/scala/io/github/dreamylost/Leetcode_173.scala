/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 173. 二叉搜索树迭代器
  *
  * 实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
  *
  * 调用 next() 将返回二叉搜索树中的下一个最小的数。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-21
  * @version v1.0
  */
object Leetcode_173 extends App {

  var obj = new BSTIterator(TreeNodeData.treeData3_5())
  var param_1 = obj.next()
  var param_2 = obj.hasNext()
  println(param_1)
  println(param_2)

  /**
    * 中序入栈，每次弹出栈顶的并添加右子树
    * 1096 ms,100.00%
    * 67.7 MB,100.00%
    *
    * @param _root
    */
  class BSTIterator(_root: TreeNode) {

    var stack = List.empty[TreeNode]
    pushLeft(_root)

    /** @return the next smallest number */
    def next(): Int = {
      val node = stack.last
      stack = stack.init
      if (node != null) {
        pushLeft(node.right)
        return node.value
      }
      -1
    }

    /** @return whether we have a next smallest number */
    def hasNext(): Boolean = stack.nonEmpty

    private[this] def pushLeft(r: TreeNode): Unit = {
      if (r == null) return
      var t = r
      while (t != null) {
        stack = stack ::: List(t)
        t = t.left
      }
    }

  }

}
