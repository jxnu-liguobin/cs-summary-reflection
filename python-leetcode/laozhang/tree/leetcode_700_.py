#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
700. 二叉树中的搜索
"""
from laozhang import TreeNode


class Solution:
    res = None

    def searchBST(self, root: TreeNode, val: int) -> TreeNode:
        if root is None:
            return
        if root.val == val:
            self.res = root
            return self.res
        self.searchBST(root.left, val)
        self.searchBST(root.right, val)
        return self.res
