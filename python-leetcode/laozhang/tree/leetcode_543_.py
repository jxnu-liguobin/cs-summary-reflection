#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
543. 二叉树的直径
"""
from laozhang import TreeNode


class Solution:
    max = 0

    def diameterOfBinaryTree(self, root: TreeNode) -> int:

        if not root or (not root.left and not root.right):
            return 0

        def helper(root: TreeNode):
            if not root:
                return 0

            a = helper(root.left) + 1
            b = helper(root.right) + 1
            #记录某个节点的最大直径
            self.max = self.max if a + b - 2 < self.max else a + b - 2
            res = a if a > b else b
            return res

        helper(root)

        return self.max
