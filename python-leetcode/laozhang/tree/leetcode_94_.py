#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
94. 二叉树的中序遍历
"""
from typing import List

from laozhang import TreeNode


class Solution:
    def inorderTraversal(self, root: TreeNode) -> List[int]:
        stack = []
        list = []
        while root or len(stack) > 0:
            while root:
                stack.append(root)
                root = root.left

            node = stack.pop()
            list.append(node.val)
            root = node.right

        return list
