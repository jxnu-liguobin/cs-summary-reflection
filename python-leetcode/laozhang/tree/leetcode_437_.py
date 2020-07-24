#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
437. 路径总和 III
"""
from laozhang import TreeNode


class Solution:
    def pathSum(self, root: TreeNode, sum: int) -> int:
        def helper(root: TreeNode, sum: int):
            if not root:
                return 0

            count = sumHelper(root, sum)
            a = helper(root.left, sum)
            b = helper(root.right, sum)
            return a + b + count

        def sumHelper(root: TreeNode, sum) -> int:
            if not root:
                return 0
            sum = sum - root.val
            count = (1 if sum == 0 else 0)
            return count + sumHelper(root.left, sum) + sumHelper(root.right, sum)

        return helper(root, sum)
