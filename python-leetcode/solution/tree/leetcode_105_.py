#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
105. 从前序与中序遍历序列构造二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    ##待优化
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        if not preorder:
            return None
        val = preorder[0]
        k = inorder.index(val)
        root = TreeNode(val)
        root.left = self.buildTree(preorder[1:k + 1], inorder[:k])
        root.right = self.buildTree(preorder[k + 1:], inorder[k + 1:])
        return root
