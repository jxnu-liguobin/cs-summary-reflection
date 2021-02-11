Leetcode Rust 实现
--

超简单的算法题目，主要为了熟悉rust语法。源码在old_solutions.rs(未使用单测的)或leetcode-*.rs(使用了rust单测的)，70%~90%是双100。

若有其他人想贡献Rust的LeetCode，请参照现有格式新增文件（同时将文件导入到`main.rs`）。如果题目已经存在，可以在`leetcode_xx.rs`中添加第二个方法实现。

## 树

* [面试题 55 - I 二叉树的深度](src/interview_55_1.rs) Easy
* [面试题 04.02 最小高度树](src/interview_04_02.rs) Easy
* [938 二叉搜索树的范围和](src/leetcode_938.rs) Easy
* [面试题 54 二叉搜索树的第k大节点](src/interview_54.rs) Easy
* [面试题 32 - II. 从上到下打印二叉树 II](src/interview_32_2.rs) Easy
* [101 对称二叉树](src/leetcode_101.rs) Easy
* [107 二叉树的层次遍历 II](src/leetcode_107.rs) Easy
* [110 平衡二叉树](src/leetcode_110.rs) Easy
* [111 二叉树的最小深度](src/leetcode_111.rs) Easy
* [112 路径总和](src/leetcode_112.rs) Easy
* [687 最长同值路径](src/leetcode_687.rs) Easy
* [257 二叉树的所有路径](src/leetcode_257.rs) Easy
* [1443 收集树上所有苹果的最少时间](src/leetcode_1443.rs) Medium
* [515 在每个树行中找最大值](src/leetcode_515.rs) Medium
* [1145 二叉树着色游戏](src/leetcode_1145.rs) Medium
* [面试题 04.12. 求和路径](src/interview_04_12.rs) Medium
* [面试题 04.10. 检查子树](src/interview_04_10.rs) Medium
* [面试题 04.03. 特定深度节点链表](src/interview_04_03.rs) Medium

### unsafe写法

* [617 合并二叉树](src/leetcode_617.rs) Easy
* [100 相同的树](src/leetcode_100.rs) Easy
* [1367 二叉树中的列表](src/leetcode_1367.rs) Medium

## 链表&栈&队列

* [面试题 22 链表中倒数第k个节点](src/interview_22.rs) Easy
* [面试题 06 从尾到头打印链表](src/interview_06.rs) Easy
* [面试题 24 反转链表](src/interview_24.rs) Easy
* [面试题 25 合并两个排序的链表](src/interview_25.rs) Easy
* [876 链表的中间结点](src/leetcode_876.rs) Easy
* [83 删除排序链表中的重复元素](src/leetcode_83.rs) Easy
* [面试题 02.02 返回倒数第 k 个节点值](src/interview_02_02.rs) Easy
* [面试题 03.04 化栈为队](src/interview_03_04.rs) Easy
* [面试题 09 用两个栈实现队列](src/interview_09.rs) Easy
* [20 有效的括号](src/leetcode_20.rs) Easy
* [1021 删除最外层的括号](src/leetcode_1021.rs) Easy
* [933 最近的请求次数](src/leetcode_933.rs) Easy

## 其他

* [1351 统计有序矩阵中的负数](src/leetcode_1351.rs) Easy
* [1380 矩阵中的幸运数](src/leetcode_1380.rs) Easy
* [1385 两个数组间的距离值](src/leetcode_1385.rs) Easy
* [977 有序数组的平方](src/leetcode_977.rs) Easy
* [561 数组拆分 I](src/leetcode_561.rs) Easy
* [905 按奇偶排序数组](src/leetcode_905.rs) Easy
* [1403 非递增顺序的最小子序列](src/leetcode_1403.rs) Easy
* [1281 整数的各位积和之差](src/leetcode_1281.rs) Easy
* [面试题 58 - II 左旋转字符串](src/interview_58_2.rs) Easy
* [1365 有多少小于当前数字的数字](src/leetcode_1365.rs) Easy
* [1342 将数字变成 0 的操作次数](src/leetcode_1342.rs) Easy
* [1313 解压缩编码列表](src/leetcode_1313.rs) Easy
* [面试题 17 打印从1到最大的n位数](src/interview_17.rs) Easy
* [面试题 05 替换空格](src/interview_05.rs) Easy
* [1221 分割平衡字符串](src/leetcode_1221.rs) Easy
* [1252 奇数值单元格的数目](src/leetcode_1252.rs) Easy
* [1323 6 和 9 组成的最大数字](src/leetcode_1323.rs) Easy
* [461 汉明距离](src/leetcode_461.rs) Easy
* [709 转换成小写字母](src/leetcode_709.rs) Easy
* [1304 和为零的N个唯一整数](src/leetcode_1304.rs) Easy
* [804 唯一摩尔斯密码词](src/leetcode_804.rs) Easy
* [832 翻转图像](src/leetcode_832.rs) Easy
* [1370 上升下降字符串](src/leetcode_1370.rs) Easy
* [1051 高度检查器](src/leetcode_1051.rs) Easy
* [728 自除数](src/leetcode_728.rs) Easy
* [面试题 01.01 判定字符是否唯一](src/interview_01_01.rs) Easy
* [面试题 16.07 最大数值](src/interview_16_07.rs) Easy
* [1374 生成每种字符都是奇数个的字符串](src/leetcode_1374.rs) Easy
* [557 反转字符串中的单词 III](src/leetcode_557.rs) Easy
* [999 可以被一步捕获的棋子数](src/leetcode_999.rs) Easy
* [292 Nim 游戏](src/leetcode_292.rs) Easy
* [1160 拼写单词](src/leetcode_1160.rs) Easy
* [1413 逐步求和得到正数的最小值](src/leetcode_1413.rs) Easy
* [944 删列造序](src/leetcode_944.rs) Easy
* [9 回文数](src/leetcode_9.rs) Easy
* [13 罗马数字转整数](src/leetcode_13.rs) Easy
* [500 键盘行](src/leetcode_500.rs) Easy
* [14 最长公共前缀](src/leetcode_14.rs) Easy
* [35 搜索插入位置](src/leetcode_35.rs) Easy
* [1207 独一无二的出现次数](src/leetcode_1207.rs) Easy
* [38 外观数列](src/leetcode_38.rs) Easy
* [58 最后一个单词的长度](src/leetcode_58.rs) Easy
* [665 非递减数列](src/leetcode_665.rs) Easy
* [66 加一](src/leetcode_66.rs) Easy
* [67 二进制求和](src/leetcode_67.rs) Easy
* [475 供暖器](src/leetcode_475.rs) Easy
* [605 种花问题](src/leetcode_605.rs) Easy
* [面试题10- I 斐波那契数列](src/interview_10_01.rs) Easy
* [633 平方数之和](src/leetcode_633.rs) Easy
* [204 计数质数](src/leetcode_204.rs) Easy
* [686 重复叠加字符串匹配](src/leetcode_686.rs) Easy
* [面试题 08.01 三步问题](src/interview_08_01.rs) Easy
* [532 数组中的K-diff数对](src/leetcode_532.rs) Easy
* [840 矩阵中的幻方](src/leetcode_840.rs) Easy
* [581 最短无序连续子数组](src/leetcode_581.rs) Easy
* [321. 拼接最大数](src/leetcode_321.rs) Hard
* [118. 杨辉三角](src/leetcode_118.rs) Hard
* [860. 柠檬水找零](src/leetcode_118.rs) Easy
* [738. 单调递增的数字](src/leetcode_738.rs) Easy
* [448. 找到所有数组中消失的数字](src/leetcode_448.rs) Easy
* [1480. 一维数组的动态和](src/leetcode_1180.rs) Easy
* [703. 数据流中的第 K 大元素](src/leetcode_703.rs) Easy
