#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1379. 找出克隆二叉树中的相同节点
"""
from laozhang import TreeNode


class Solution:
    def getTargetCopy(self, original: TreeNode, cloned: TreeNode, target: TreeNode) -> TreeNode:
        if original and cloned:
            if original.val == target.val:
                return cloned
            a = self.getTargetCopy(original.left, cloned.left, target)
            if not a:
                b = self.getTargetCopy(original.right, cloned.right, target)
                return b
            else:
                return a

