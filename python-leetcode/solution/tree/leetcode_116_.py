#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
116. 填充每个节点的下一个右侧节点指针
"""
from solution import Node


class Solution:
    """
    思路:
    1.第一步首先讲父节点的左右子节点连起来
    2.第二步连接其他节点
    3.递归重复
    """

    def connect(self, root: 'Node') -> 'Node':

        def dfs(root: Node):
            if root:
                if root.left and root.right:
                    # 连接左右子节点
                    root.left.next = root.right
                    # 关键步骤通过1步骤构建的连接来讲同一层的所有节点连接起来
                    if root.next:
                        root.right.next = root.next.left
                dfs(root.left)
                dfs(root.right)

        dfs(root)
        return root
