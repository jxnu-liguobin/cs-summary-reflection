/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 *
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * @author 梦境迷离 dreamylost
 * @since 2020-07-12
 * @version v1.0
 */
object Leetcode_116 extends App {

  val ret = connect(Node.getNode_7())
  println(ret)

  /**
   * 左子树的next就是右子树，右子树的next就是next节点的左子树
   * 要求不能使用额外空间
   *
   * 572 ms,75.00%
   * 51.9 MB,100.00%
   *
   * @param root
   * @return
   */
  def connect(root: Node): Node = {
    if (root == null) return null
    if (root.left != null) root.left.next = root.right
    if (root.right != null && root.next != null) {
      root.right.next = root.next.left
    }
    connect(root.left)
    connect(root.right)
    root
  }

}
