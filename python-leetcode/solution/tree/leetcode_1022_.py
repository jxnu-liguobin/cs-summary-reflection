#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1022.  从根到叶的二进制数之和
"""
from solution import TreeNode


class Solution:
    def sumRootToLeaf(self, root: TreeNode) -> int:
        res = []
        count = 0

        def helper(root: TreeNode, j: str):
            if root:
                if not root.left and not root.right:
                    j += str(root.val)
                    res.append(j)
                else:
                    j = j + str(root.val)
                    helper(root.left, j)
                    helper(root.right, j)

        helper(root, '')
        for r in res:
            c = int(r, 2)
            count += c
        return count
