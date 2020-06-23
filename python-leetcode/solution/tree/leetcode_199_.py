#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
106. 从中序与后序遍历序列构造二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    depth = -1

    def rightSideView(self, root: TreeNode) -> List[int]:
        list = []

        def dfs(root: TreeNode, k: int, res: List[int]) -> int:
            if not root:
                return 0
            if k > self.depth:
                res.append(root.val)
                self.depth = k

            dfs(root.right, k + 1, res)
            dfs(root.left, k + 1, res)

        if not root:
            return list
        list.append(root.val)
        dfs(root.right, 1, list)
        dfs(root.left, 1, list)

        return list
