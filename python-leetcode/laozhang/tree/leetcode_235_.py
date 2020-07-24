#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# coding=utf-8
"""
235. 二叉搜索树的最近公共祖先
"""
from laozhang import TreeNode


class Solution:

    """"
    从根节点开始遍历树
    如果节点 pp 和节点 qq 都在右子树上，那么以右孩子为根节点继续 1 的操作
    如果节点 pp 和节点 qq 都在左子树上，那么以左孩子为根节点继续 1 的操作
    如果条件 2 和条件 3 都不成立，这就意味着我们已经找到节 pp 和节点 qq 的 LCA 了
    """
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        if not root:
            return

        rootValue = root.val

        if p.val > rootValue and q.val > rootValue:
            return self.lowestCommonAncestor(root.right, p, q)

        if p.val < rootValue and q.val < rootValue:
            return self.lowestCommonAncestor(root.left, p, q)

        return root
