#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
572. 另一个树的子树
"""
from laozhang import TreeNode


class Solution:
    """
    思路:利用dfs搜索符合条件的节点
    1.首先在树节点当中找到与另一个树根节点相等的节点
    2.判断树的左右子树是否和另一个子树节点相同
    3.重复进行1的步骤
    退出循环的条件:
    1.节点值是否相同
    2.是否同时达到叶子节点
    3.利用or连接递归，如果在左边子树找到了，直接返回true这样就不用遍历右节点了

   """
    def isSubtree(self, s: TreeNode, t: TreeNode) -> bool:
        def dfs(s: TreeNode, t: TreeNode):
            if not s:
                return False
            return isSameTree(s, t) or dfs(s.left, t) or dfs(s.right, t)

        def isSameTree(s: TreeNode, t: TreeNode):
            if not s and not t:
                return True
            if not s or not t:
                return False
            if s.val != t.val:
                return False
            return isSameTree(s.left, t.left) and isSameTree(s.right, t.right)

        return dfs(s, t)
