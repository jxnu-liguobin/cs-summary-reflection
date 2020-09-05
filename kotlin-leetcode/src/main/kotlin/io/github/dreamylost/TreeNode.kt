/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * kotlin 普通类，可空的二叉树结构
 * @author 梦境迷离
 * @version 1.0,2020/6/22
 */
class TreeNode(var `val`: Int) {
    // data class 的 copy不是增量拷贝，非构造函数中的属性会丢失，这里left和right会变成null，实际就是调用构造函数
    var left: TreeNode? = null
    var right: TreeNode? = null
}
