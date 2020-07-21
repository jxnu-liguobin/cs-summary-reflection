#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
897. 递增顺序查找二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    def increasingBST(self, root: TreeNode) -> TreeNode:
        res = []

        def helper(root: TreeNode) -> List[int]:
            if not root:
                return
            helper(root.left)
            res.append(root.val)
            helper(root.right)
            return res

        helper(root)
        current = TreeNode(res[0])
        root = current
        for index, value in enumerate(res):
            if index + 1 >= len(res):
                break
            current.right = TreeNode(res[index + 1])
            current = current.right

        return root
