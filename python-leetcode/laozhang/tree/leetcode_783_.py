#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
783. 二叉搜索树节点最小距离
"""
from laozhang import TreeNode
class Solution:
    def minDiffInBST(self, root: TreeNode) -> int:
        res = 1 << 32 - 1
        temp = -1

        def helper(root: TreeNode):
            nonlocal res, temp
            if root:
                helper(root.left)
                if res > (root.val - temp) and temp >= 0:
                    res = root.val - temp
                temp = root.val
                helper(root.right)

        helper(root)
        return res
