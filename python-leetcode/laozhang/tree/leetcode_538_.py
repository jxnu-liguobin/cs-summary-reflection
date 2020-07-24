#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
538. 二叉搜索树转换为累加树
"""
from laozhang import TreeNode


class Solution:
    num=0
    def convertBST(self, root: TreeNode) -> TreeNode:
        if root:
            self.convertBST(root.right)
            root.val+=self.num
            self.num=root.val
            self.convertBST(root.left)
            return root

