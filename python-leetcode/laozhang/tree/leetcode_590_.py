#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
590. N叉树的后续遍历
"""
from typing import List

from laozhang import Node


class Solution:
    def postorder(self, root: 'Node') -> List[int]:
        result = []

        def postHelper(root):
            if not root:
                return None
            for child in root.children:
                postHelper(child)
                result.append(child.val)

        if not root:
            return result
        postHelper(root)
        result.append(root.val)
        return result
