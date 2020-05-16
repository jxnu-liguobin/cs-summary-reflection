#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
27. 二叉树的镜像
"""
from solution import TreeNode


class Solution:
    def mirrorTree(self, root: TreeNode) -> TreeNode:
        # 为空返回
        if not root: return
        # 保存左节点，就像a，b互换值需要一个临时变量
        temp = root.left
        root.left = self.mirrorTree(root.right)
        root.right = self.mirrorTree(temp)
        return root
