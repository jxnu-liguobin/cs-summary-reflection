#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
114. 二叉树展开为链表
"""
from solution import TreeNode


class Solution:
    pre = None

    def flatten(self, root: TreeNode) -> None:
        """
        Do not return anything, modify root in-place instead.
        """
        if not root:
            return None
        self.flatten(root.right)
        self.flatten(root.left)
        root.right = self.pre
        root.left = None
        self.pre = root
