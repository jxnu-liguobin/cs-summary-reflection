#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
27. 二叉树的镜像

所以其他Python代码参考本例格式，通用数据结构放在init中，禁止使用Python2

可增加main方法和测速case 或 ac结果

提交前使用IDEA的format code格式化代码
"""
from laozhang import TreeNode


class Solution:
    def mirrorTree(self, root: TreeNode) -> TreeNode:
        # 为空返回
        if not root: return
        # 保存左节点，就像a，b互换值需要一个临时变量
        temp = root.left
        root.left = self.mirrorTree(root.right)
        root.right = self.mirrorTree(temp)
        return root
