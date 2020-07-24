#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
103. 二叉树的锯齿形层次遍历
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def zigzagLevelOrder(self, root: TreeNode) -> List[List[int]]:

        res = []

        # 和层次遍历一样只不过判断一下基偶插入顺序就行了
        def dfs(root: TreeNode, k: int):
            nonlocal res
            if root:
                if k >= len(res):
                    res.append([root.val])
                else:
                    if k % 2 == 0:
                        res[k].append(root.val)
                    else:
                        res[k].insert(0, root.val)

                dfs(root.left, k + 1)
                dfs(root.right, k + 1)

        dfs(root, 0)
        return res
