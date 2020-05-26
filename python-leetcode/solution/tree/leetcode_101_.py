#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
101. 对称的二叉树
"""
from solution import TreeNode


class Solution:
    def isSymmetric(self, root: TreeNode) -> bool:
        def helper(p: TreeNode, q: TreeNode):
            if not p and not q:
                return True
            if not p or not q:
                return False
            if p.val == q.val:
                return helper(p.left, q.right) and helper(p.right, q.left)
            return False

        return helper(root, root)
