#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
404. 左叶子之和
"""
from solution import TreeNode


class Solution:
    sum = 0

    def sumOfLeftLeaves(self, root: TreeNode) -> int:
        if root:
            self.sumOfLeftLeaves(root.left)
            if root.left and not root.left.left and not root.left.right:
                self.sum += root.left.val
            self.sumOfLeftLeaves(root.right)
        return self.sum
