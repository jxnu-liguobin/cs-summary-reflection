#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
226. 反转二叉树
"""
from solution import TreeNode


class Solution:
    def invertTree(self, root: TreeNode) -> TreeNode:
        if root is None:
            return None
        temp = root.right
        root.right = self.invertTree(root.left)
        root.left = self.invertTree(temp)
        return root
