#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
129. 求根到叶子节点数字之和
"""
from laozhang import TreeNode


class Solution:
    class Solution:
        def sumNumbers(self, root: TreeNode) -> int:
            res = 0

            def dfs(root: TreeNode, sum: int) -> int:
                nonlocal res
                if root:
                    sum = sum * 10 + root.val
                    if not root.left and not root.right:
                        res += sum
                    dfs(root.left, sum)
                    dfs(root.right, sum)

            dfs(root, 0)
            return res
