#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
814. 二叉树剪枝
"""
from laozhang import TreeNode


class Solution:
    def pruneTree(self, root: TreeNode) -> TreeNode:

        def helper(root: TreeNode) -> TreeNode:
            if root:
                helper(root.left)
                helper(root.right)
                if root:
                    if root.left and root.left.val == 0 and not root.left.left and not root.left.right:
                        root.left = None
                    if root.right and root.right.val == 0 and not root.right.right and not root.right.left:
                        root.right = None
        helper(root)
        return root
