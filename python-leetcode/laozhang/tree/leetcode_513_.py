#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
513. 找树左下角的值
"""
from laozhang import TreeNode


class Solution:
    def findBottomLeftValue(self, root: TreeNode) -> int:
        list=[]

        def helper(root:TreeNode,k:int):
            nonlocal list
            if root:
                if k>len(list):
                    temp=[]
                    temp.append(root.val)
                    list.append(temp)
                else:
                    list[k-1].append(root.val)
                helper(root.left,k+1)
                helper(root.right,k+1)
        helper(root,1)
        return list[-1][0]