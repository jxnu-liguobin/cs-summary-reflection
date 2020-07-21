#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
515. 在每个树行中找最大值
"""
from typing import List

from solution import TreeNode


class Solution:
    def largestValues(self, root: TreeNode) -> List[int]:

        res = []

        def dfs(root: TreeNode, k: int):
            nonlocal res
            if not root:
                return None

            if k >= len(res):
                res.append(root.val)
            else:
                if res[k] < root.val:
                    res[k] = root.val
            dfs(root.left, k + 1)
            dfs(root.right, k + 1)

        dfs(root, 0)
        return res
