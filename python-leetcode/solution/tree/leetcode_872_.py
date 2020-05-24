#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
872. 叶子相似的树
"""
from solution import TreeNode


class Solution:
    def leafSimilar(self, root1: TreeNode, root2: TreeNode) -> bool:
        list1 = []
        list2 = []

        def helper(root: TreeNode, res):
            if root:
                if not root.left and not root.right:
                    res.append(root.val)
                helper(root.left, res)
                helper(root.right, res)

        helper(root1, list1)
        helper(root2, list2)

        return list1 == list2
