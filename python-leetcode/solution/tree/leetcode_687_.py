#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
687. 最长同值路径
"""
from solution import TreeNode


class Solution:
    def longestUnivaluePath(self, root: TreeNode) -> int:
        max = 0

        def dfs(root: TreeNode):
            nonlocal max
            if not root:
                return 0

            a = dfs(root.left)
            b = dfs(root.right)

            if root.left and root.val == root.left.val:
                a += 1
            else:
                a = 0
            if root.right and root.val == root.right.val:
                b += 1
            else:
                b = 0

            max = max if a + b < max else a + b
            res = a if a > b else b
            return res

        dfs(root)
        return max
