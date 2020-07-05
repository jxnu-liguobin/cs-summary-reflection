#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
112. 路径总和
"""

from solution import TreeNode


class Solution:
    def hasPathSum(self, root: TreeNode, sum: int) -> bool:
        if not root:
            return False
        sum = sum - root.val
        if sum == 0 and not root.left and not root.right:
            return True
        a = self.hasPathSum(root.left, sum)
        if not a:
            return self.hasPathSum(root.right, sum)
        return a
