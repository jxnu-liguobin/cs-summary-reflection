#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
100. 相同的树
"""
from solution import TreeNode


class Solution:
    def isSameTree(self, p: TreeNode, q: TreeNode) -> bool:
        res = True

        def helper(p: TreeNode, q: TreeNode):
            nonlocal res
            if not p and not q:
                return
            if not p or not q:
                res = False
                return
            if p.val != q.val:
                res = False
            helper(p.left, q.left)
            helper(p.right, q.right)

        helper(p, q)
        return res
