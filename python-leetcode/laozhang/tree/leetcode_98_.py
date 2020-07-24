#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
98. 验证二叉搜索树
"""
from laozhang import TreeNode


class Solution:
    def isValidBST(self, root: TreeNode) -> bool:
        temp = -2 ** 32

        def dfs(root: TreeNode) -> bool:
            nonlocal temp
            if not root:
                return True
            a = dfs(root.left)
            if root.val <= temp:
                return False
            temp = root.val
            if a:
                b = dfs(root.right)
                return b
            return a

        return dfs(root)
