#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
32. 二叉树的层次遍历
"""
from solution import TreeNode


class Solution:
    res = []

    def levelOrder(self, root: TreeNode) -> List[List[int]]:

        if not root:
            return []

        def helper(root: TreeNode, k):
            if not root:
                return
            if k >= len(self.res):
                l = []
                self.res.append(l)
            self.res[k].append(root.val)
            helper(root.left, k + 1)
            helper(root.right, k + 1)

        helper(root, k=0)
        return self.res
