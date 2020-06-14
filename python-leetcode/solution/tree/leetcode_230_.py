#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
230. 二叉搜索树中第K小的元素
"""
from solution import TreeNode


class Solution:
    def kthSmallest(self, root: TreeNode, k: int) -> int:
        count = 0
        min = 0

        def helper(root: TreeNode, k: int):
            nonlocal count, min
            if root:
                helper(root.left, k)
                count += 1
                if count == k:
                    min = root.val
                helper(root.right, k)

        helper(root, k)
        return min
