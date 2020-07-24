#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
257. 二叉树的所有路径
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def binaryTreePaths(self, root: TreeNode) -> List[str]:

        res = []

        def helper(root: TreeNode, s: str):
            if root:
                if not root.left and not root.right:
                    s += str(root.val)
                    res.append(s)
                else:
                    s += str(root.val)
                    s += '->'
                    helper(root.left, s)
                    helper(root.right, s)

        helper(root, '')
        return res
