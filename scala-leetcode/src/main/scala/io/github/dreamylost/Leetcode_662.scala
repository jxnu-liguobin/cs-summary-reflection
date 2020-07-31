/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 662. 二叉树最大宽度
  *
  * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
  *
  * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
  *
  * @see https://github.com/jxnu-liguobin/cs-summary-reflection
  * @author 梦境迷离
  * @version 1.0,2020/7/22
  */
object Leetcode_662 extends App {

  val ret = widthOfBinaryTree(TreeNodeData.treeData3())
  println(ret)

  /**
    * 层序遍历，记录每个节点的索引
    * 600 ms,100.00%
    * 51.6 MB,100.00%
    *
    * @param root
    * @return
    */
  def widthOfBinaryTree(root: TreeNode): Int = {
    if (root == null) return 0
    if (root.left == null && root.right == null) return 1
    var ret = 1
    var nodeQueue = List[TreeNode]()
    nodeQueue = nodeQueue ::: List(root)
    var indexedQueue = Seq.empty[Int]
    indexedQueue = indexedQueue ++ Seq(1)
    var size = 1
    while (nodeQueue.nonEmpty) {
      size -= 1
      val index = indexedQueue.head
      indexedQueue = indexedQueue.tail
      val node = nodeQueue.head
      nodeQueue = nodeQueue.tail
      //根据满二叉树子树下标与父节点的关系，记录左右子树的下标位置
      if (node.left != null) {
        nodeQueue = nodeQueue ::: List(node.left)
        indexedQueue = indexedQueue ++ Seq(2 * index)
      }
      if (node.right != null) {
        nodeQueue = nodeQueue ::: List(node.right)
        indexedQueue = indexedQueue ++ Seq(2 * index + 1)
      }
      //在遍历完当前层时，判断下标是否存在大于2个以上的元素，就使用两个边界计算宽度
      if (size == 0) {
        if (indexedQueue.size >= 2) {
          ret = math.max(ret, indexedQueue.last - indexedQueue.head + 1)
        }
        size = nodeQueue.size
      }
    }
    ret
  }
}
