#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1008. 先序遍历构造二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    def bstFromPreorder(self, preorder: List[int]) -> TreeNode:
        def helper(root: TreeNode, val: int) -> TreeNode:
            if not root:
                return TreeNode(val)

            if val > root.val:
                root.right = helper(root.right, val)
            else:
                root.left = helper(root.left, val)
            return root

        root = TreeNode(preorder[0])
        for v in preorder[1:]:
            helper(root, v)
        return root
