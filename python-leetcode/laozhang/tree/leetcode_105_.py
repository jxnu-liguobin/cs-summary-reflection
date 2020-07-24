#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
105. 从前序与中序遍历序列构造二叉树
"""
from typing import List

from laozhang import TreeNode


class Solution:
    ##待优化
    def buildTree_(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        if not preorder:
            return None
        val = preorder[0]
        k = inorder.index(val)
        root = TreeNode(val)
        root.left = self.buildTree(preorder[1:k + 1], inorder[:k])
        root.right = self.buildTree(preorder[k + 1:], inorder[k + 1:])
        return root

    ##优化版
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        def helper(preorder: List[int], inorder: List[int], preleft: int, preright: int, inleft: int, inright: int):
            if inleft > inright:
                return None
            val = preorder[preleft]
            mid = inorder.index(val)
            root = TreeNode(val)
            root.left = helper(preorder, inorder, preleft + 1, mid - inleft + preleft, inleft, mid - 1)
            root.right = helper(preorder, inorder, mid - inleft + preleft + 1, preright, mid + 1, inright)
            return root

        return helper(preorder, inorder, 0, len(preorder) - 1, 0, len(inorder) - 1)
