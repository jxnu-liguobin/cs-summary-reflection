---
title: Redis设计与实现笔记_1
categories:
- Redis
tags: [Redis数据结构]
---

* 目录
{:toc}

### 1.字符串 

- 结构体 使用redis3.0源码，之后的源码由于考虑到内存对齐，结构体有多个。下面其他数据结构都是使用3.0的源码。
    - sds.h/sdshdr
```c
struct sdshdr {
   unsigned int len; //记录buf中已经使用的数量，为sds保存的字符串的长度
   unsigned int free; //记录buf中未使用的数量
   char buf[]; //保存字符串
};
```
- 使用场景
    当Redis需要的不仅是一个字符串字面量，而是一个可以被修改的字符串值时，使用sds表示字符串值
- 常数时间复杂度获取字符串长度
- 杜绝缓冲区溢出
- 减少修改字符串长度时所需的内存分配次数
    - 增加时采用预分配策略
    - 删除时采用惰性释放策略
- 二进制安全
- 兼容部分C字符串函数

### 2.链表

- 结构体 
    - adlist.h/list
    - adlist.h/listNode
```c
typedef struct list {
   listNode *head; // 链表头指针
   listNode *tail; // 链表头尾指针
   void *(*dup)(void *ptr); //节点复制函数
   void (*free)(void *ptr); //节点释放函数
   int (*match)(void *ptr, void *key); //节点对比函数，这三个函数用于为节点值设定特定类型的函数来实现保存不同类型的值。多态
   unsigned long len; //链表包含的节点数量
} list;

typedef struct listNode {
   struct listNode *prev; //链表前置节点指针
   struct listNode *next; //链表后缀节点指针
   void *value; //链表的值
} listNode;
```
- 使用场景
    - 列表键包含数量比较多的元素，或列表中包含的元素都是长度比较长的字符串，使用链表作为列表键的底层实现
- 双端链表，可以快速获取前置和后置节点，时间复杂度为O(1)
- 无环
- 使用list结构来持有listNode结构，list结构为链表提供了head、tail、len三个属性，可快速获取头尾和链表长度，时间复杂度为O(1)
- list结构提供了dup、free、match三个属性，用于为节点值设置类型特点函数，故list可以用于存储各种不同类型的值

### 3.字典

- 结构体
    - dict.h/dict 字典
    - dict.h/dictht 哈希表
    - dict.h/dictEntry 哈希表的节点
    - dict.h/dictType 用于设置哈希表节点的类型的结构
```c
typedef struct dictEntry {
   void *key; //哈希表的键值对中的键
   union { //哈希表的值，联合：同一时刻只能有一个成员允许含有一个值
       void *val;
       uint64_t u64;
       int64_t s64;
       double d;
   } v;
   struct dictEntry *next; //指向下一个哈希表节点，形成链表
} dictEntry;

typedef struct dictType {
   unsigned int (*hashFunction)(const void *key); //哈希函数
   void *(*keyDup)(void *privdata, const void *key); //键复制函数
   void *(*valDup)(void *privdata, const void *obj); //值复制函数
   int (*keyCompare)(void *privdata, const void *key1, const void *key2); //键对比函数
   void (*keyDestructor)(void *privdata, void *key); //键析构函数
   void (*valDestructor)(void *privdata, void *obj); //值析构函数
} dictType;

/* This is our hash table structure. Every dictionary has two of this as we
 * implement incremental rehashing, for the old to the new table. */
typedef struct dictht {
    dictEntry **table; //哈希表数组
    unsigned long size; //哈希表大小
    unsigned long sizemask; //哈希表大小掩码，用于计算索引，总是为size-1
    unsigned long used; //记录哈希表已有节点的数量
} dictht;

typedef struct dict {
    dictType *type; //类型特点函数
    void *privdata; //私有数据
    dictht ht[2]; //2个哈希表，一个用于重哈希
    long rehashidx; /* rehashing not in progress if rehashidx == -1 */ //rehash索引
    int iterators; /* number of iterators currently running */
} dict
```    
- 使用场景
    - 哈希键包含的键值对比较多，或键值对中的元素的都是比较长的字符串，使用字典作为哈希键的底层实现
    - Redis数据库
- 每个字典有两个哈希表
    - ht[0] 平时使用，rehash时，包含的键值对数量只减不增
    - ht[1] rehash时使用，在字典查找键时，先在ht[0]中找，再到ht[1]中找
    - 删改查需要操作两个ht，新增仅操作ht[1]
- rehash并非一次性完成，而是渐进式地完成 
    - 使用rehashidx实现渐进式rehash
        - 初始为-1，开始rehash时置为0
        - 每次增删改查操作都递增1
        - 完成rehash时，置为-1
- 使用MurmurHash2算法来计算键的哈希值
- 哈希表使用链地址法来解决哈希冲突，同一索引上的多个键值对会被连接成一个单向链表
    - 链表采用头插法
- 哈希表的自适操作
    - 拓展
        - 服务器当前没有在执行bgsave或bgrewriteaof且负载因子大于等于1
        - 服务器当前正在执行bgsave或bgrewriteaof且负载因子大于等于5
    - 收缩
        - 哈希表的负载因子小于0.1   
- rehash步骤
    - 为ht[1]分配空间，空间大小取决于要执行的操作和ht[0]当前包含的键值对数量（ht[0].used属性的值）
    - 将保存在ht[0]中的所有键值对rehash到ht[1]上，rehash指的是重新计算索引值和哈希值
    - 当ht[0]包含的所有键值对都迁移到了ht[1]后，ht[0]变为空表，释放ht[0]，将ht[1]设置为ht[0]，并在ht[1]新创建一个空白哈希表，为下次rehash准备

### 4.跳跃表

- 结构体 
    - server.h/zskiplist 3.0及以下版本在redis.h中
    - server.h/zskiplistNode
```c
/* ZSETs use a specialized version of Skiplists */
typedef struct zskiplistNode {
  robj *obj; //成员对象
  double score; //分值
  struct zskiplistNode *backward; //后退指针
  struct zskiplistLevel { //跳跃表的层
      struct zskiplistNode *forward; //前进指针
      unsigned int span; //跨度
  } level[];
} zskiplistNode;

typedef struct zskiplist {
  struct zskiplistNode *header, *tail; //表头和表尾节点
  unsigned long length; //表中节点的数量
  int level; //表中层数最大的节点的层数
} zskiplist;
```    
- 使用场景
    - 有序集合包含的元素比较多，或有序集合中元素的成员是比较长的字符串（即zskiplistNode的obj属性），使用跳跃表作为有序集合的底层实现
- Redis的跳跃表由zskiplist和zskiplistNode两个结构组成，其中zskiplist用于保存跳跃表信息（表头、表尾、长度），而zskiplistNode则用于表示跳跃表节点
- 每个跳跃表的层高都是1至32之间的随机数
- 同一个跳跃表中，多个节点可以保护相同的值，但每个节点的成员对象必须是唯一的
- 跳跃表的节点按照分值大小进行排序，当分值相同时，节点按照成员对象的大小进行排序（字典序）

### 5.整数集合

- 结构体
    - intset.h/intset
```c
typedef struct intset {
  uint32_t encoding; //编码方式
  uint32_t length; //集合包含的元素数量
  int8_t contents[]; //保存元素的数组，取决于encoding的值，会升级
} intset;
```
- 使用场景
    - 当集合只包含整数值的元素，并且这个集合元素的数量不多时，使用整数集合作为集合键的底层实现
- 整数集合底层实现为数组，这个数组以有序，无重复的方式保存元素，在有需要时会根据添加的元素的类型，改变这个数组的类型
- 整数集合的升级 
    - 提升操作上的灵活性
    - 尽可能地节约内存
    - 不支持降级

### 6.压缩列表

- 结构体
    - 无
- 使用场景
    - 列表键只包含少量列表项，并且每个列表项要么就是小整数值，要么就是长度比较短的字符串，使用压缩列表作为底层实现
    - 哈希键只包含少量键值对，并且每个键和值要么就是小整数值，要么就是长度比较短的字符串，使用压缩列表作为底层实现
- 为节约内存而开发的顺序型数据结构（本身无新定义的结构体）    
- 可以包含多个节点，每个节点可以保存一个字节数组或整数值
- 添加新节点到压缩列表，或从压缩列表中删除节点，可能会引发连锁更新操作，但这种操作的出现几率并不高
    - 连锁更新
        - 指增加或删除压缩列表的节点后，引发的必须更新previous_entry_length属性的操作
        - 最坏需要N次空间重分配操作
        - 每次分配的最坏复杂度为O(N)，故连锁更新的最坏复杂度为O(N^2)
        - 压缩列表里要恰好有多个连续的、长度介于250~253字节之间的节点，才可能引发连锁更新，实际并不多见
        - 即使出现连锁更新，但被更新的元素不多，也不会对性能造成影响
        - ziplistPush等命令的平均复杂度为O(N)
- 压缩列表的节点
    - previous_entry_length 记录前一个节点的长度，可以为1或5，单位字节
        - 前一节点的长度小于254字节，previous_entry_length属性需要用1字节长空间来保存这个值
        - 前一节点的长度大于等于254字节，previous_entry_length属性需要用5字节长空间来保存这个值
    - encoding 记录了节点的content属性保存的数据的类型和长度，可以为1，2，5，单位字节（整数仅使用1字节，整数编码6种，字节数组编码3种，最高两位总是作为编码的类型）
    - content 节点的值，可以为整数或字节数组，值的类型和长度由encoding属性决定
    
### 7.对象

- 结构体
    - server.h/redisObject 3.0及以下版本在redis.h中
```c
typedef struct redisObject {
  unsigned type:4; //对象类型
  unsigned encoding:4; //编码
  unsigned lru:LRU_BITS; /* lru time (relative to server.lruclock) */ //记录LRU/LFU信息
  int refcount; //引用计数
  void *ptr; //指向底层实现数据结构的指针
} robj;
```
- 与保存数据相关的三个属性    
    - type Redis对象底层使用的数据结构类型
        - 字符串对象（整数值、embstr编码的sds、sds）  
        - 列表对象（压缩列表、双端链表）
        - 哈希对象（压缩列表、字典）
        - 集合对象（整数集、字典）
        - 有序集合对象（压缩列表、跳跃表和字典）
    - encoding 记录对象所使用的编码（每个编码对应一种底层结构）
    - ptr 指向底层实现数据结构的指针  
- Redis数据库中的每个键值对的键和值都是一个对象
- Redis有上述五种类型的对象，每种类型至少有两种或以上的编码方式，不同的编码可以在不同的使用场景上优化对象的使用效率
- 服务器在执行某些命令之前，会先检查给定键的类型能否执行指定的命令（检查键的值对象的类型）
- Redis的对象系统使用带有引用计数实现的内存回收机制，当一个对象不再被使用，对象所占用的内存会被自动释放
- Redis会共享0~9999的字符串的对象
- 对象会记录自己的最后一次被访问的时间，这个时间可以用于计算对象的空转时间