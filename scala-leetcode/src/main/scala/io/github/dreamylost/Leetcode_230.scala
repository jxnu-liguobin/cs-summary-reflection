/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 230. 二叉搜索树中第K小的元素
  *
  * 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。
  *
  * @see https://github.com/jxnu-liguobin
  * @author dreamylost
  * @since 2020-07-25
  * @version v1.0
  */
object Leetcode_230 extends App {

  val ret = kthSmallest(TreeNodeData.treeData3_5(), 2)
  val ret2 = kthSmallest2(TreeNodeData.treeData3_5(), 2)
  println(ret)
  println(ret2)

  /**
    * 暴力
    *
    * 736 ms,100.00%
    * 53 MB,100.00%
    *
    * @param root
    * @param k
    * @return
    */
  def kthSmallest(root: TreeNode, k: Int): Int = {
    var count = 0
    var ret = 0

    def inorder(r: TreeNode): Unit = {
      if (r == null) return
      inorder(r.left)
      count += 1
      if (count == k) {
        ret = r.value
      }
      inorder(r.right)
    }

    inorder(root)
    ret
  }

  /**
    * 788 ms,80.00%
    * 55.9 MB,100.00%
    *
    * @param root
    * @param k
    * @return
    */
  def kthSmallest2(root: TreeNode, k: Int): Int = {
    implicit val ord: Ordering[Int] = (x: Int, y: Int) => if (x > y) -1 else 1
    val queue = new scala.collection.mutable.PriorityQueue[Int]()

    def helper(r: TreeNode): Unit = {
      if (r == null) return
      helper(r.left)
      queue.enqueue(r.value)
      helper(r.right)
    }

    helper(root)
    if (queue.size >= k) {
      (0 until k - 1) foreach { _ =>
        queue.dequeue()
      }
    }
    queue.head
  }
}
