/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 606. 根据二叉树创建字符串
 *
 * 你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。
 *
 * 空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
 *
 * @author 梦境迷离 dreamylost
 * @since 2020-06-14
 * @version v1.0
 */
object Leetcode_606 extends App {
  val ret = tree2str(TreeNodeData.treeData3_4())
  println(ret)

  /**
   * 1128 ms,50.00%
   * 66.4 MB,100.00%
   *
   * @param t
   * @return
   */
  def tree2str(t: TreeNode): String = {
    //输出前序遍历结果，其中子节点使用()包住，左边的空括号对不能省略，右边的需要省略，只处理左节点为空时的空括号对
    var result = ""
    if (t == null) return result
    else if (t.left == null && t.right == null) return t.value.toString
    else {
      result = t.value.toString
      if (t.left != null) {
        result += s"(${tree2str(t.left)})"
      } else result += "()"
      if (t.right != null) {
        result += s"(${tree2str(t.right)})"
      }
    }
    result
  }
}
