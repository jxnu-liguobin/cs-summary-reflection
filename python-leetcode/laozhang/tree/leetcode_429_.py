#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
429. N叉树的层序遍历
"""
from typing import List

from laozhang import Node


class Solution:
    def levelOrder(self, root: 'Node') -> List[List[int]]:
        res = []

        def dfs(root: Node, k: int):
            nonlocal res
            if not root:
                return None
            if len(res) <= k:
                res.append([])
            res[k].append(root.val)
            for chi in root.children:
                dfs(chi, k + 1)

        dfs(root, 0)
        return res
