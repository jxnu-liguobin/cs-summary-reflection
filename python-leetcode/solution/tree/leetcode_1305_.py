#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
1305. 两棵二叉搜索树中的所有元素
"""
from typing import List

from solution import TreeNode


class Solution:
    def getAllElements(self, root1: TreeNode, root2: TreeNode) -> List[int]:

        list1 = []
        list2 = []
        list = []

        def helper(root: TreeNode, res: List[int]):
            if root:
                helper(root.left, res)
                res.append(root.val)
                helper(root.right, res)

        helper(root1, list1)
        helper(root2, list2)
        var1 = 0
        var2 = 0
        l1 = len(list1)
        l2 = len(list2)
        while var1 < l1 and var2 < l2:
            if list1[var1] > list2[var2]:
                list.append(list2[var2])
                var2 += 1
            else:
                list.append(list1[var1])
                var1 += 1
        if var1 < l1:
            return list + list1[var1:l1]
        if var2 < l2:
            return list + list2[var2:l2]
        return list

