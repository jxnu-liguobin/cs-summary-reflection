#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
106. 从中序与后序遍历序列构造二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    def buildTree(self, inorder: List[int], postorder: List[int]) -> TreeNode:
        if not postorder:
            return None
        # 从后续遍历中获取根节点
        v = postorder[-1]
        # 从中序遍历中获取左右子树的分割点
        n = inorder.index(v)
        # 创建根节点
        root = TreeNode(v)
        # 根据n来构建二叉树进行分割递归
        root.left = self.buildTree(inorder[:n], postorder[:n])
        root.right = self.buildTree(inorder[n + 1:], postorder[n:-1])
        return root
