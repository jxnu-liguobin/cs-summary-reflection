#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
671.二叉树中第二小的节点
"""
from laozhang import TreeNode


class Solution:
    def findSecondMinimumValue(self, root: TreeNode) -> int:

        def helper(root: TreeNode, k: int):
            if not root:
                return -1
            if not root.left:
                return -1

            if root.left:
                left = root.left.val
                right = root.right.val
                if left == k:
                    left = helper(root.left, k)
                if right == k:
                    right = helper(root.right, k)

                if left != -1 and right != -1:
                    return left if left < right else right
                if left != -1:
                    return left
                return right

        return helper(root, root.val)
