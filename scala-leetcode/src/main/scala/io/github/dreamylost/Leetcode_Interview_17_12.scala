/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 面试题 17.12. BiNode
 *
 * 二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
 * 实现一个方法，把二叉搜索树转换为单向链表，要求值的顺序保持不变，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。
 *
 * 返回转换后的单向链表的头节点。
 *
 * @author 梦境迷离
 * @version 1.0,2020/6/24
 */
object Leetcode_Interview_17_12 extends App {

  val ret = convertBiNode(TreeNodeData.treeData3_5())
  val ret2 = convertBiNode2(TreeNodeData.treeData3_5())
  println(ret)
  println(ret2)

  /**
   * 使用java集合模拟栈，也超内存
   * 33 / 34 个通过测试用例
   *
   * @param root
   * @return
   */
  def convertBiNode(root: TreeNode): TreeNode = {
    import java.util
    val head = new TreeNode(Int.MinValue)
    var preNode = head
    //ArrayList、LinkedList都超内存，无解
    val stack = new util.LinkedList[TreeNode]
    if (root == null) return null
    var cur = root
    while (cur != null || !stack.isEmpty) {
      while (cur != null) {
        stack.addLast(cur)
        cur = cur.left
      }
      val node = stack.pollLast
      node.left = null
      preNode.right = node
      preNode = node
      cur = node.right
    }
    head.right
  }

  /**
   * 使用函数式栈，超内存
   * 33 / 34 个通过测试用例
   *
   * @param root
   * @return
   */
  def convertBiNode_(root: TreeNode): TreeNode = {
    val head = new TreeNode(Int.MinValue)
    var preNode = head
    var stack = List[TreeNode]()
    if (root == null) return null
    var cur = root
    while (cur != null || stack.nonEmpty) {
      while (cur != null) {
        stack = cur :: stack
        cur = cur.left
      }
      val (node, s) = stack.head -> stack.tail
      node.left = null
      preNode.right = node
      preNode = node

      stack = s
      cur = node.right
    }
    head.right
  }

  /**
   * 使用递归
   * 33 / 34 个通过测试用例，最后超过内存限制了。。
   *
   * @param root
   * @return
   */
  @unchecked
  def convertBiNode2(root: TreeNode): TreeNode = {
    val head = new TreeNode(Int.MinValue)
    var preNode = head

    def helper(r: TreeNode): Unit = {
      if (r == null) return
      helper(r.left)
      //1.中序遍历，在访问当前节点时，左边的已经访问了，所以可以直接置为空
      r.left = null
      //2.前置节点的右指针指向当前节点
      preNode.right = r
      //3.更新前置节点位置
      preNode = r
      helper(r.right)
    }

    helper(root)
    //4.返回头结点的右子树作为结果
    head.right
  }
}
