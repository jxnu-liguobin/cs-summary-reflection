#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
55. 二叉树的深度
"""
from laozhang import TreeNode


class Solution:
    def maxDepth(self, root: TreeNode) -> int:
        if root is None:
            return 0
        leftDepth = self.maxDepth(root.left) + 1
        rightDepth = self.maxDepth(root.right) + 1
        return leftDepth if leftDepth > rightDepth else rightDepth
