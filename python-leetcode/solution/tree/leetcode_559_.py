#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
559. N叉树的最大深度
"""
from solution import Node


class Solution:
    def maxDepth(self, root: 'Node') -> int:
        list = []

        def dfs(root: Node, k: int) -> int:
            nonlocal list
            if root:
                for c in root.children:
                    if len(list) < k:
                        list.append(root)
                    dfs(c, k + 1)

        if not root:
            return 0
        dfs(root, 1)
        return len(list) + 1
