#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
222. 完全二叉树的节点个数
"""
from solution import TreeNode


class Solution:
    def countNodes(self, root: TreeNode) -> int:
        if not root:
            return 0

        a = self.countNodes(root.left)
        b = self.countNodes(root.right)

        return a+b+1
