package io.github.dreamylost

//隐藏java集合

import java.util.{ Queue => _ }

import scala.collection.mutable

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
  def preorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret.:+(node.value)
        stack.push(node.right) // 先右后左，保证左子树先遍历
        stack.push(node.left)
      }
    }
    ret
  }

  //中， 94. Binary Tree Inorder Traversal (Medium)
  def inorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    if (root == null) return ret
    var cur = root
    while (cur != null || stack.nonEmpty) {
      while (cur != null) {
        stack.push(cur)
        cur = cur.left
      }
      val node = stack.pop()
      ret.:+(node.value)
      cur = node.right
    }
    ret
  }

  /**
   * [因为是栈，先左子树出栈，后右子树出栈]
   * 前序遍历为 root -> left -> right，后序遍历为 left -> right -> root。可以修改前序遍历成为 root -> right -> left，那么这个顺序就和后序遍历正好相反。
   */
  //后，145. Binary Tree Postorder Traversal (Medium)
  def postorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret.:+(node.value)
        stack.push(node.left) // 先右后左，保证左子树先遍历
        stack.push(node.right)
      }
    }
    ret.reverse
  }

  //层序
  def levelTraverse(root: TreeNode): mutable.Seq[Int] = {
    if (root == null) return mutable.Seq()
    val list = mutable.Seq[Int]();
    //Scala的Seq将是Java的List，Scala的List将是Java的LinkedList。
    val queue = mutable.Queue[TreeNode]() //层序遍历时保存结点的队列，可以省略new或者省略()
    queue.enqueue(root)
    //初始化
    while (queue.nonEmpty) {
      val node = queue.dequeue
      list.:+(node.value)
      if (node.left != null) queue.enqueue(node.left)
      if (node.right != null) queue.enqueue(node.right)
    }

    list
  }


}