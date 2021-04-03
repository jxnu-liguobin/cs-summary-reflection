/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 449. 序列化和反序列化二叉搜索树
 *
 * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
 *
 * 设计一个算法来序列化和反序列化二叉搜索树。 对序列化/反序列化算法的工作方式没有限制。
 * 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
 *
 * 编码的字符串应尽可能紧凑。
 *
 * @see https://github.com/jxnu-liguobin
 * @author 梦境迷离
 * @since 2020-08-01
 * @version 1.0
 */
object Leetcode_449 extends App {

  val serialize = new Codec().serialize(TreeNodeData.treeData2())
  val deserialize = new Codec().deserialize(serialize)
  println(TreeNodeData.treeData2())
  println(serialize)
  println(deserialize)

  /**
   * 不能直接转为中序，因为为null时构造会丢失子节点，所以必须需要前序或中序
   *
   * 1544 ms,100.00%
   * 64.1 MB,100.00%
   */
  class Codec {
    // Encodes a list of strings to a single string.
    def serialize(root: TreeNode): String = {
      if (root == null) return ""
      var s: String = root.value + "#" //使用字节表示的字符串存储数字节省空间
      s += serialize(root.left)
      s += serialize(root.right)
      s
    }

    // Decodes a single string to a list of strings.
    def deserialize(s: String): TreeNode = {
      def buildSearchTree(values: Seq[String], l: Int, r: Int): TreeNode = {
        if (l > r) {
          return null
        }
        val root = new TreeNode(values(l).toInt)
        // 找到第一个不满足左子树的节点
        var index = l
        while (index <= r && values(index).toInt <= root.value) index += 1
        root.left = buildSearchTree(values, l + 1, index - 1)
        root.right = buildSearchTree(values, index, r)
        root
      }

      if (s == "") return null
      val values = s.split("#")
      buildSearchTree(values, 0, values.length - 1)
    }
  }

}
