#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1315. 祖父节点值为偶数的节点和
"""
from solution import TreeNode


class Solution:
    def sumEvenGrandparent(self, root: TreeNode) -> int:
        sum = 0

        def dfs(root: TreeNode) -> int:
            nonlocal sum
            if not root:
                return 0
            if root.val % 2 == 0:
                if root.left and root.left.left:
                    sum += root.left.left.val
                if root.left and root.left.right:
                    sum += root.left.right.val
                if root.right and root.right.right:
                    sum += root.right.right.val
                if root.right and root.right.left:
                    sum += root.right.left.val
            dfs(root.left)
            dfs(root.right)

        dfs(root)
        return sum
