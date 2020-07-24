#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
965. 单值二叉树
"""
from laozhang import TreeNode


# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def isUnivalTree(self, root: TreeNode) -> bool:

        def helper(root:TreeNode,value: int):
            if not root:
                return True

            if root.val != value:
                return False
            return helper(root.left,value) and helper(root.right,value)

        return helper(root,root.val)


