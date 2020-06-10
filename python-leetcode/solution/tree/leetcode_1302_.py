#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1302. 层数最深叶子节点的和
"""
from solution import TreeNode


class Solution:
    def deepestLeavesSum(self, root: TreeNode) -> int:

        max_depth = 0
        map = {}

        def helper(root: TreeNode, depth: int):
            nonlocal max_depth, map
            if root:
                if depth > max_depth:
                    max_depth = depth
                helper(root.left, depth + 1)
                helper(root.right, depth + 1)
                if not root.left and not root.right and depth == max_depth:
                    temp = map.get(depth, 0)
                    map[depth] = temp + root.val

        helper(root, 1)
        for key in map:
            if key > max_depth:
                max_depth = key
        return map[max_depth]
