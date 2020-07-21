#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
117. 填充每个节点的下一个右侧节点指针 II
"""
from solution import Node


class Solution:
    """
    0.分三种情况
    1.如果根节点包含左右子节点则通过root.left.next = root.right连接
    2.根节点只有左节点的情况则找到同层节点中离此节点最近的一个进行相连即:root.left.next = getNextNode(root.next)
    3.根节点只包含右节点的情况则找到同层节点中离此节点最近的一个进行相连即:root.right.next = getNextNode(root.next)
    4.重复1进行dfs递归
    5.注意：递归时要先递归右子树，否则上级节点next关系没建好，下级无法成功getNextNode
    """
    def connect(self, root: 'Node') -> 'Node':

        def getNextNode(root: Node):
            if not root:
                return None
            if root.left:
                return root.left
            if root.right:
                return root.right
            if root.next:
                return getNextNode(root.next)

        def dfs(root: Node):
            if root:
                if root.left and root.right:
                    root.left.next = root.right
                if root.left and not root.right:
                    root.left.next = getNextNode(root.next)
                if root.right:
                    root.right.next = getNextNode(root.next)
                dfs(root.right)
                dfs(root.left)

        dfs(root)
        return root
