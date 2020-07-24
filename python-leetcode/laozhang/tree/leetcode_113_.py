#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
113. 路径总和 II
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def pathSum(self, root: TreeNode, sum: int) -> List[List[int]]:
        res = []

        def dfs(root: TreeNode, sum: int, list) -> int:
            nonlocal res
            if not root:
                return 0
            sum = sum - root.val
            if sum == 0 and not root.left and not root.right:
                res.append(list)
            if root.left:
                dfs(root.left, sum, list + [root.left.val])
            if root.right:
                dfs(root.right, sum, list + [root.right.val])

        if not root:
            return res
        dfs(root, sum, [root.val])
        return res
