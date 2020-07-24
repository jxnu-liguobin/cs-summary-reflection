#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
107. 二叉树的层次遍历II
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def levelOrderBottom(self, root: TreeNode) -> List[List[int]]:
        res = []

        def helper(root: TreeNode, k: int):
            if not root:
                return
            if len(res) <= k:
                res.append([])
            res[k].append(root.val)
            helper(root.left, k + 1)
            helper(root.right, k + 1)

        helper(root, 0)
        return res[::-1]
