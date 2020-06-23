package io.github.dreamylost

/**
  * @author 梦境迷离 dreamylost
  * @since 2020-06-07
  * @version v1.0
  */
object TreeNodeData {

  def treeData3(): TreeNode = {
    val root = new TreeNode(2)
    val r1 = new TreeNode(3)
    val l1 = new TreeNode(1)
    root.right = r1
    r1.left = l1
    root
  }

  def treeData1(): TreeNode = {
    val root = new TreeNode(2147483647)
    root
  }

  def treeData3_1(): TreeNode = {
    val root = new TreeNode(1)
    val r1 = new TreeNode(5)
    val l1 = new TreeNode(3)
    root.right = r1
    r1.left = l1
    root
  }

  def treeData3_2(): TreeNode = {
    val root = new TreeNode(1)
    val r1 = new TreeNode(5)
    val l1 = new TreeNode(5)
    root.right = r1
    root.left = l1
    root
  }

  def treeData3_3(): TreeNode = {
    val root = new TreeNode(5)
    val r1 = new TreeNode(5)
    val l1 = new TreeNode(6)
    root.right = r1
    root.left = l1
    root
  }

  //搜索树
  def treeData3_4(): TreeNode = {
    val root = new TreeNode(2)
    val r1 = new TreeNode(3)
    val l1 = new TreeNode(1)
    root.right = r1
    root.left = l1
    root
  }

  //搜索树
  def treeData3_5(): TreeNode = {
    val root = new TreeNode(1)
    val r1 = new TreeNode(2)
    val l1 = new TreeNode(0)
    root.right = r1
    root.left = l1
    root
  }

  //10 11
  def treeData3_6(): TreeNode = {
    val root = new TreeNode(1)
    val r1 = new TreeNode(1)
    val l1 = new TreeNode(0)
    root.right = r1
    root.left = l1
    root
  }

  def treeData5_1(): TreeNode = {
    val n1 = new TreeNode(1)
    val n2 = new TreeNode(2)
    val n3 = new TreeNode(3)
    val n4 = new TreeNode(4)
    val n5 = new TreeNode(5)

    n1.left = n2
    n1.right = n3
    n2.right = n4
    n3.right = n5

    n1
  }
}
