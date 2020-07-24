#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
501. 二叉搜索树中的众数
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def findMode(self, root: TreeNode) -> List[int]:
        list = []
        res = {}
        max = 0

        def helper(root: TreeNode):
            nonlocal res
            if root:
                if res.get(root.val, 0) == 0:
                    res[root.val] = 1
                else:
                    res[root.val] += 1
                helper(root.left)
                helper(root.right)

        helper(root)
        for key, value in res.items():
            if value > max:
                list.clear()
                list.append(key)
                max = value
            elif value < max:
                continue
            else:
                list.append(key)

        return list
