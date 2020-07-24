#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
993. 二叉树的堂兄弟节点
"""
from laozhang import TreeNode


class Solution:
    def isCousins(self, root: TreeNode, x: int, y: int) -> bool:

        def helper(root: TreeNode, target: int, f, depth, fatherNode):
            if root:
                depth += 1
                if root.val == target:
                    fatherNode = f
                    return depth, fatherNode
                a = helper(root.left, target, root.val, depth, fatherNode)
                if not a:
                    b = helper(root.right, target, root.val, depth, fatherNode)
                    return b
                return a

        a = helper(root, x, root.val, depth=0, fatherNode=0)
        b = helper(root, y, root.val, depth=0, fatherNode=0)
        if a[0] != b[0]:
            return False
        if b[1] == a[1]:
            return False
        return True
