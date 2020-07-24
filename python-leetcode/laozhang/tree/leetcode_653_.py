#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
653. 两数之和 IV - 输入 BST
"""
from laozhang import TreeNode


class Solution:
    def findTarget(self, root: TreeNode, k: int) -> bool:
        res = []

        def helper(root: TreeNode):
            nonlocal res
            if root:
                helper(root.left)
                res.append(root.val)
                helper(root.right)

        helper(root)
        start = 0
        end = len(res) - 1
        while start < end:
            if (res[start] + res[end]) > k:
                end = end - 1
            elif (res[start] + res[end]) < k:
                start = start + 1
            else:
                return True
        return False
