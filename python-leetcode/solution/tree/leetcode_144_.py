#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
144. 二叉树的前序遍历
"""
from typing import List

from solution import TreeNode


class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:

        stack = []
        res = []

        def dfs(root: TreeNode) -> List[int]:
            nonlocal stack, res
            while stack or root:
                while root:
                    res.append(root.val)
                    stack.append(root)
                    root = root.left

                node = stack.pop()
                root = node.right

        dfs(root)
        return res
