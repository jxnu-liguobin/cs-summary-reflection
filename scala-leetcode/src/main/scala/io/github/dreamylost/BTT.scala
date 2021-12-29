/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

//隐藏java集合

import java.util.{ Queue => _ }
import scala.collection.mutable.ListBuffer

//过期，以后使用List
import scala.collection.immutable.Queue

/**
 * 二叉树的遍历
 */
object BTT extends App {

  //前
  def qiandfs(root: TreeNode) {
    visit(root)
    qiandfs(root.left)
    qiandfs(root.right)
  }

  //中
  def zhongdfs(root: TreeNode) {
    zhongdfs(root.left)
    visit(root)
    zhongdfs(root.right)
  }

  //后
  def houdfs(root: TreeNode) {
    houdfs(root.left)
    houdfs(root.right)
    visit(root)
  }

  val visit = (root: TreeNode) => {
    println(root.value)
  }

  //前，144. Binary Tree Preorder Traversal (Medium)
  @unchecked
  private def preOrder(root: TreeNode): Array[Int] = {
    if (root == null) return Array()
    val queue = new java.util.LinkedList[TreeNode]
    queue.addLast(root);
    val preRet = ListBuffer[Int]()
    while (!queue.isEmpty()) {
      val element = queue.removeLast()
      if (element != null) {
        preRet.append(element.value)
        if (element.right != null) queue.addLast(element.right)
        if (element.left != null) queue.addLast(element.left)
      }
    }
    preRet.toArray
  }

  /**
   * 中序
   * @param root
   * @return
   */
  private def inOrder(root: TreeNode): Array[Int] = {
    if (root == null) return Array()
    val stack = new java.util.LinkedList[TreeNode]
    val inRet = ListBuffer[Int]()
    var cur = root
    while (cur != null || !stack.isEmpty()) {
      while (cur != null) {
        stack.addLast(cur)
        cur = cur.left
      }
      val element = stack.removeLast()
      if (element != null) {
        inRet.append(element.value)
        cur = element.right
      }
    }
    inRet.toArray
  }

  /**
   * [因为是栈，先左子树出栈，后右子树出栈]
   * 前序遍历为 root -> left -> right，后序遍历为 left -> right -> root。可以修改前序遍历成为 root -> right -> left，那么这个顺序就和后序遍历正好相反。
   */
  //后，145. Binary Tree Postorder Traversal (Medium)
  private def postOrder(root: TreeNode): Array[Int] = {
    if (root == null) return Array()
    val stack = new java.util.LinkedList[TreeNode]
    stack.addLast(root);
    val preRet = ListBuffer[Int]()
    while (!stack.isEmpty()) {
      val element = stack.removeLast()
      preRet.append(element.value)
      if (element.left != null) stack.addLast(element.left)
      if (element.right != null) stack.addLast(element.right)
    }
    preRet.reverse.toArray
  }

  //层序
  def levelOrder(root: TreeNode): List[List[Int]] = {
    var ret = List[List[Int]]()
    if (root == null) return ret
    var queue = List[TreeNode]()
    queue = queue ::: List(root)
    while (queue.nonEmpty) {
      var levelValue = List.empty[Int]
      val queueSize = queue.size
      for (_ <- 0 until queueSize) {
        val node = queue.head
        queue = queue.tail
        //注意: 对于::或:::方法，a.::(b) 等价 b::a
        levelValue = levelValue ::: List(node.value)
        if (node.left != null) queue = queue ::: List(node.left)
        if (node.right != null) queue = queue ::: List(node.right)
      }
      ret = ret ::: List(levelValue)
    }
    ret
  }

  //根据中序的有序数组构造二叉树
  def buildSearchTree(values: Seq[Int], l: Int, r: Int): TreeNode = {
    if (l > r) {
      return null
    }
    if (l == r) {
      return new TreeNode(values(l))
    }
    val mid = l + (r - l) / 2
    val root = new TreeNode(values(mid))
    root.left = buildSearchTree(values, l, mid - 1)
    root.right = buildSearchTree(values, mid + 1, r)
    root
  }
}
