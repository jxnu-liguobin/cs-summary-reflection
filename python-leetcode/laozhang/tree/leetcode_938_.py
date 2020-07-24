#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
938. 二叉搜索树的范围和
"""
from laozhang import TreeNode


class Solution:
    """
    1.首先判断当前节点在不在LR范围内
    2. 判断当前节点是不是大于最大值R,如果大于则其他范围内的值只能去左子树里面查找
    3. 判断当前节点是不是小于最小值L,如果小于则其他范围内的值只能去右子树里面查找
    4. 如果当前节点在L-R范围内则左右子树都需要遍历
    5. 重复1步骤
    """

    def rangeSumBST(self, root: TreeNode, L: int, R: int) -> int:
        res = 0

        def dfs(root: TreeNode, L: int, R: int):
            nonlocal res
            if root:
                if L <= root.val <= R:
                    res += root.val
                if root.val >= R:
                    dfs(root.left, L, R)
                elif root.val <= L:
                    dfs(root.right, L, R)
                else:
                    dfs(root.left, L, R)
                    dfs(root.right, L, R)

        dfs(root, L, R)
        return res
