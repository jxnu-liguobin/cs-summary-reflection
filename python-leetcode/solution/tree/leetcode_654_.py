#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
654. 最大二叉树
"""
from typing import List

from solution import TreeNode


class Solution:
    def constructMaximumBinaryTree(self, nums: List[int]) -> TreeNode:

        def helper(nums: List[int], start: int, end: int):
            if start <= end:
                value = max(nums[start:end + 1])
                index = nums.index(value)
                node = TreeNode(value)
                node.left = helper(nums, start, index - 1)
                node.right = helper(nums, index + 1, end)
                return node
            else:
                return None

        return helper(nums, 0, len(nums) - 1)
