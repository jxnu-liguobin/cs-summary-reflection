#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
110. 平衡二叉树
"""
from solution import TreeNode


class Solution:
    res = True

    def isBalanced(self, root: TreeNode) -> bool:
        def helper(root: TreeNode):
            if not root:
                return 0

            a = helper(root.left) + 1
            b = helper(root.right) + 1
            if abs(a - b) > 1:
                self.res = False
            return max(a, b)

        helper(root)
        return self.res
