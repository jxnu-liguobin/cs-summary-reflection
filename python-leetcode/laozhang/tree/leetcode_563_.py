#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
563. 二叉树的坡度
"""
from laozhang import TreeNode


class Solution:
    def findTilt(self, root: TreeNode) -> int:
        res = 0

        def helper(root: TreeNode):
            nonlocal res
            if not root:
                return 0
            left = helper(root.left)
            right = helper(root.right)
            res += abs(left - right)
            return left + right + root.val

        helper(root)
        return res
