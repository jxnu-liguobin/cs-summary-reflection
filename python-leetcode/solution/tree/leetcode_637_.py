#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
637. 二叉树的层平均值
"""
from typing import List

from solution import TreeNode


class Solution:
    def averageOfLevels(self, root: TreeNode) -> List[float]:
        res = []
        average = []

        def helper(root: TreeNode, k: int):
            if not root:
                return
            if len(res) <= k:
                res.append([])
            res[k].append(root.val)
            helper(root.left, k + 1)
            helper(root.right, k + 1)

        helper(root, 0)
        for li in res:
            average.append(sum(li) / len(li))
        return average
