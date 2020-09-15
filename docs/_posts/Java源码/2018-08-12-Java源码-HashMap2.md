---
title: HashMap2
categories:
- Java源码
tags: [源码分析]
description: HashMap补充
---

* 目录
{:toc}

HashMap可以说是Java项目里最常用的集合类了，作为一种典型的K-V存储的数据结构。
它的底层是由数组-链表组成，当添加新元素时，它会根据元素的hash值找到对应的"桶"，也就是HashMap源码中`Node<K, V>` 里的元素，
并插入到对应位置的链表中，链表元素个数过长时会转化为红黑树（JDK1.8+）。

HashMap常见问题：

1. HashMap的底层存储结构是怎样的啊？
2. 线程安全吗？为什么不安全？
3. 1.7和1.8版本的HashMap有什么区别？1.7的有什么隐患，什么原因导致的？
4. hashcode是唯一的吗？插入元素的时候怎么比较的？
5. 与HashTable，ConcurrentHashMap有什么区别？

### 为什么要转成红黑树呢？

我们都知道，链表取元素是从头结点一直遍历到对应的结点，这个过程的复杂度是`O(N)` ，而红黑树基于二叉树的结构，查找元素的复杂度为`O(logN)` ，所以，当元素个数过多时，用红黑树存储可以提高搜索的效率。

既然红黑树的效率高，那怎么不一开始就用红黑树存储呢？

这其实是基于空间和时间平衡的考虑，JDK的源码里已经对这个问题做了解释：

```
Because TreeNodes are about twice the size of regular nodes, we
use them only when bins contain enough nodes to warrant use
(see TREEIFY_THRESHOLD). And when they become too small (due to
removal or resizing) they are converted back to plain bins. 
Ideally, under random hashCodes, the frequency ofnodes in bins follows a Poisson distribution
```

看注释里的前面四行就不难理解，单个TreeNode需要占用的空间大约是普通Node的两倍，所以只有当包含足够多的Node时才会转成TreeNode，这个足够多的标准就是由`TREEIFY_THRESHOLD`的值（默认值8）决定的。
而当桶中节点数由于移除或者resize（扩容) 变少后，红黑树会转变为普通的链表，这个阈值是`UNTREEIFY_THRESHOLD`（默认值6）。

```
/**
* The bin count threshold for using a tree rather than list for a
* bin.  Bins are converted to trees when adding an element to a
* bin with at least this many nodes. The value must be greater
* than 2 and should be at least 8 to mesh with assumptions in
* tree removal about conversion back to plain bins upon
* shrinkage.
*/
static final int TREEIFY_THRESHOLD = 8;

/**
* The bin count threshold for untreeifying a (split) bin during a
* resize operation. Should be less than TREEIFY_THRESHOLD, and at
* most 6 to mesh with shrinkage detection under removal.
*/
static final int UNTREEIFY_THRESHOLD = 6;
```

看到这里就不难明白了，红黑树虽然查询效率比链表高，但是结点占用的空间大，只有达到一定的数目才有树化的意义，这是基于时间和空间的平衡考虑。

### 为什么树化标准是8个

至于为什么树化标准的数量是8个，在源码中，上面那段笔记后面还有一段较长的注释，我们可以从那一段注释中找到答案，原文是这样：

```
usages with well-distributed user hashCodes, tree bins are
rarely used.  Ideally, under random hashCodes, the frequency of
nodes in bins follows a Poisson distribution
(http://en.wikipedia.org/wiki/Poisson_distribution) with a
parameter of about 0.5 on average for the default resizing
threshold of 0.75, although with a large variance because of
resizing granularity. Ignoring variance, the expected
occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
factorial(k)). The first values are:

 0:    0.60653066
 1:    0.30326533
 2:    0.07581633
 3:    0.01263606
 4:    0.00157952
 5:    0.00015795
 6:    0.00001316
 7:    0.00000094
 8:    0.00000006
 more: less than 1 in ten million
```

大概意思就是：如果hashCode的分布离散良好的话，那么红黑树是很少会被用到的，因为各个值都均匀分布，很少出现链表很长的情况。
在理想情况下，链表长度符合泊松分布，各个长度的命中概率依次递减，注释中给我们展示了1-8长度的具体命中概率，当长度为8的时候，概率概率仅为0.00000006，这么小的概率，HashMap的红黑树转换几乎不会发生，因为我们日常使用不会存储那么多的数据。

当然，这是理想的算法，但不妨某些用户使用HashMap过程导致hashCode分布离散很差的场景，这个时候再转换为红黑树就是一种很好的退让策略。

首先说明一下，在HashMap中，决定某个对象落在哪一个“桶“，是由该对象的hashCode决定的，JDK无法阻止用户实现自己的哈希算法，如果用户重写了hashCode，并且算法实现比较差的话，
就很可能会使HashMap的链表变得很长（可能导致元素都被挂载在一个桶上），时间复杂度变成`O(N)`，此时相当于在链表查找。

### hash方法

说到哈希算法，我们再来扩充一个知识点，这也是我觉得HashMap中非常牛逼的设计之一。

在HashMap的源码中，存储对象hashCode的计算是由hash()方法决定的，hash()是HashMap中的核心函数，在存储数据时，将key传入中进行运算，得出key的哈希值，通过这个哈希值运算才能获取key应该放置在“桶”的哪个位置，下面是方法的源码：

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

从代码中可以看出，传入key之后，hash()会获取key的hashCode进行无符号右移16位，然后进行按位异或，并把运算后的值返回，这个值就是key的哈希值。这样运算是为了减少碰撞冲突，因为大部分元素的hashCode在低位是相同的，不做处理的话很容易造成冲突。

除了做16位位移的处理，在添加元素的方法中，HashMap还把该hash值与`table.length - 1`，也就是“桶”数组的大小做与运算，得到的结果就是对应的“桶”数组的下标，从而找到该元素所属的链表。源码里这样的：

```java
// n的值是table.length
if ((p = tab[i = (n - 1) & hash]) == null)
    tab[i] = newNode(hash, key, value, null);
```

当查找不到对应的索引时，就会新建一个新的结点作为链表的头结点。那么这里为什么要用`i = (n - 1) & hash`作为索引运算呢？

这其实是一种优化手段，由于数组的大小永远是一个2次幂，在扩容之后，一个元素的新索引要么是在原位置，要么就是在原位置加上扩容前的容量。这个方法的巧妙之处全在于&运算。
之前提到过&运算只会关注n – 1（n为数组长度）的有效位，当扩容之后，n的有效位相比之前会多增加一位（n会变成之前的二倍，所以确保数组长度永远是2次幂很重要），然后只需要判断hash在新增的有效位的位置是0还是1就可以算出新的索引位置，如果是0，那么索引没有发生变化，如果是1，索引就为原索引加上扩容前的容量。

通过位运算，在每次扩容时都不用重新计算hash，省去了不少时间，而且新增有效位是0还是1是带有随机性的，之前两个碰撞的Entry又有可能在扩容时再次均匀地散布开，达到较好的分布离散效果。

### 为什么HashMap中的&位必须为奇数

> 当然，这里不是一个普通奇数，而是2的幂次减1

从key映射到HashMap数组的对应位置需要一个Hash函数：

```java
index = Hash("hangzhou")
```

如何实现一个尽量分布均匀的hash函数呢？我们使用key的hashcode做某种运算：

```java
index = hashCode("hangzhou") & (Length - 1)
```

其中，Length为HashMap的长度，下面来演示整个过程：

1. "hangzhou"的hashcode为4740586，二进制表示为100 1000 0101 0101 1110 1010
2. 假定HashMap的长度为默认的16，则`Length - 1`为15，也就是二进制的1111
3. 把以上两个结果做与运算，得到的结果为1010，也就是index为10

可以说，Hash算法最终得到的index结果完全取决于hashCode的最后几位。

假设，HashMap的长度为10，则`Length - 1`为9，也就是二进制的1001，通过Hash算法得到的最终index为8，当只有一个元素的时候这没问题。
但是我们再来试一个hashCode：100 1000 0101 0101 1110 1110时，通过Hash算法得到的最终的index也是8，另外还有100 1000 0101 0101 1110 1000得到的index也是8。
也就是说，即使我们把倒数第二、三位的0、1变换，得到的index仍旧是8，说明有些index结果出现的几率变大！！而有些index结果永远不会出现，比如二进制0000。这样，显然不符合Hash算法均匀分布的要求。

反观，长度16或其他2的幂次方，`Length - 1`的值的二进制所有位均为1，这种情况下，Index的结果等于hashCode的最后几位。只要输入的hashCode本身符合均匀分布，Hash算法的结果就是均匀的。

### 为什么退化为链表的阈值是6

上面说到，当链表长度达到阈值8的时候会转为红黑树，但是红黑树退化为链表的阈值却是6，为什么不是小于8就退化呢？比如说7的时候就退化，偏偏要小于或等于6？

主要是一个过渡，避免链表和红黑树之间频繁的转换。如果阈值是7的话，删除一个元素红黑树就必须退化为链表，增加一个元素就必须树化，来回不断的转换结构无疑会降低性能，所以阈值才不设置的那么临近。