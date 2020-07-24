#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
701. 二叉搜索树中的插入操作
"""
from laozhang import TreeNode


class Solution:
    def insertIntoBST(self, root: TreeNode, val: int) -> TreeNode:
        if not root:
            return TreeNode(val)
        if root.val >= val:
            root.left = self.insertIntoBST(root.left, val)
        else:
            root.right = self.insertIntoBST(root.right, val)

        return root