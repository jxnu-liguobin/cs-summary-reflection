#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
173. 二叉搜索树迭代器
"""
from solution import TreeNode


class BSTIterator:
    list = []

    def __init__(self, root: TreeNode):
        while root:
            self.list.append(root)
            root = root.left

    def next(self) -> int:
        """
        @return the next smallest number
        """
        node = self.list.pop()
        t = node.right
        while (t):
            self.list.append(t)
            t = t.left

        return node.val

    def hasNext(self) -> bool:
        """
        @return whether we have a next smallest number
        """
        return len(self.list) != 0
