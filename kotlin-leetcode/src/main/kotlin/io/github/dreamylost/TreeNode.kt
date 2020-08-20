/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * kotlin 普通类，可空的二叉树结构
 * @author 梦境迷离
 * @version 1.0,2020/6/22
 */
data class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}
