/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 988. 从叶结点开始的最小字符串
  *
  * 给定一颗根结点为 root 的二叉树，树中的每一个结点都有一个从 0 到 25 的值，分别代表字母 'a' 到 'z'：值 0 代表 'a'，值 1 代表 'b'，依此类推。
  *
  * 找出按字典序最小的字符串，该字符串从这棵树的一个叶结点开始，到根结点结束。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-08
  * @version v1.0
  */
object Leetcode_988 extends App {

  val ret = smallestFromLeaf(TreeNodeData.treeData7_1())
  println(ret)

  /**
    * 1148 ms,100.00%
    * 52.9 MB,100.00%
    *
    * @param root
    * @return
    */
  def smallestFromLeaf(root: TreeNode): String = {
    var result = Seq[String]()

    def helper(r: TreeNode, path: String): Unit = {
      if (r == null) return
      if (r.left == null && r.right == null) {
        result = result ++ Seq(path + r.value + ",")
        return
      }
      if (r.left != null) helper(r.left, path + r.value + ",")
      if (r.right != null) helper(r.right, path + r.value + ",")
    }

    helper(root, new String)

    //更好的办法应在helper中翻转后马上判断，用全局变量保存最新路径
    result.map(_.split(",").map(c => (Integer.parseInt(c) + 'a').toChar).reverse.mkString).min
  }

}
