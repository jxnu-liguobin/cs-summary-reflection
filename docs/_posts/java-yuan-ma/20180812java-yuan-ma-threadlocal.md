---
title: ThreadLocal
categories:
  - Java源码
tags:
  - 源码分析
---

# 2018-08-12-Java源码-ThreadLocal

```java
package java.lang;
import java.lang.ref.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 
 * @author Josh Bloch and Doug Lea
 * @since 1.2
 */
public class ThreadLocal<T> {
    /**
     * 
     * 线程局部对象作为键，通过TraceLoad HASHCODE进行搜索 这是一个自定义哈希码（仅在TheleLoad映射中有用），可以消除冲突。
     * nextHashCode表示了即将分配的下一个ThreadLocal实例的threadLocalHashCode的值。
     */
    private final int threadLocalHashCode = nextHashCode();

    /**
     * 下一个哈希码。原子的更新。从零开始。
     */
    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * 表示了连续分配的两个ThreadLocal实例的threadLocalHashCode值的增量
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * 返回下一个哈希码
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    /**
     * 线程本地变量的初始值
     */
    protected T initialValue() {
        return null;
    }

    /**
     * 根据Supplier初始化
     * 
     * @since 1.8
     */
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }

    /**
     * 默认初始化
     */
    public ThreadLocal() {
    }

    /**
     * 返回当前线程的本地线程变量的副本值 如果变量对于当前线程没有值，则为 首先初始化为调用setInitialValue方法。
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            // 得到当前线程的Entry
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;// 返回当前线程的Entry的值
            }
        }
        return setInitialValue(); // 调用初始化
    }

    /**
     * 类似set方法
     * 
     * @return the initial value
     */
    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread(); // 当前线程
        ThreadLocalMap map = getMap(t);// 得到当前线程的ThreadLocalMap对象
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);// 新建ThreadLocalMap对象
        return value; 
    }

    /**
     * 
     * 将当前线程局部变量的副本设置为指定值。大多数子类都不需要重写这个方法。
     * 
     * 依赖initialValue方法来设置线程局部变量的值
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    /**
     * 移除此线程局部变量的当前线程的值。 如果此线程局部变量随后被当前线程的get方法访问，它的值因调用initialValue而将会被重新初始化
     * 
     * @since 1.5
     */
    public void remove() {
        ThreadLocalMap m = getMap(Thread.currentThread());
        if (m != null)
            m.remove(this);
    }

    /**
     * 获取与线程本地关联的映射map
     */
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

    /**
     * 创建与线程本地关联的映射
     * 
     * 在InheritableThreadLocal被重写。
     * 
     * 
     */
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    /**
     * 静态工厂方法，构造方法使用
     */
    static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }

    /**
     */
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * ThreadLocal的拓展
     */
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }

    /**
     * ThreadLocal最重要的数据结构
     */
    static class ThreadLocalMap {

        /**
         * 因为如果这里使用普通的key-value形式来定义存储结构，实质上就会造成节点的生命周期与线程强绑定，只要线程没有销毁，
         * 那么节点在GC分析中一直处于可达状态，没办法被回收，而程序本身也无法判断是否可以清理节点。
         * 弱引用是Java中四档引用的第三档，比软引用更加弱一些，如果一个对象没有强引用链可达，
         * 那么一般活不过下一次GC。当某个ThreadLocal已经没有强引用可达，则随着它被垃圾回收，
         * 在ThreadLocalMap里对应的Entry的键值会失效，这为ThreadLocalMap本身的垃圾清理提供了便利。
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** 与此线程本地关联的值。 */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        /**
         * 初始大小，2的幂次
         */
        private static final int INITIAL_CAPACITY = 16;

        /**
         * 
         * 大小也是2的幂次
         * 
         */
        private Entry[] table;

        /**
         * 数组中元素的数量
         */
        private int size = 0;

        /**
         * 触发扩容的临界值
         */
        private int threshold; // Default to 0

        /**
         * 设置临界值
         */
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        /**
         * Increment i modulo len. 索引+1 1、放进最前面 2、放进i+1
         */
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * Decrement i modulo len. 1、放进i-1 2、放进len-1,最后
         */
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        /**
         * 构造方法，第一次使用
         */
        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY]; // 初始化16大小的数组
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);// hash
            table[i] = new Entry(firstKey, firstValue);// 第一个节点
            size = 1;// 个数初始化为1
            setThreshold(INITIAL_CAPACITY);// 根据默认的容量设置临界值
        }

        /**
         * 构造方法，参数是ThreadLocalMap
         */
        private ThreadLocalMap(ThreadLocalMap parentMap) {
            Entry[] parentTable = parentMap.table; // 得到数组
            int len = parentTable.length;// 得到长度
            setThreshold(len);// 设置临界
            table = new Entry[len];// 创建容量相当的数组

            for (int j = 0; j < len; j++) {
                Entry e = parentTable[j];
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                    if (key != null) {
                        Object value = key.childValue(e.value);
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1); // 计算索引，类似hashmap
                        while (table[h] != null)
                            h = nextIndex(h, len); // 直到找到一个空的位置
                        table[h] = c;// 设置
                        size++;// 元素个数加+1
                    }
                }
            }
        }

        /**
         * 内部方法，获取entry
         */
        private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            if (e != null && e.get() == key)
                return e;// 散列到的位置，刚好就是要查找的对象
            else
                return getEntryAfterMiss(key, i, e);// 直接散列到的位置没找到，那么顺着hash表递增（循环）地往下找
        }

        /**
         * 
         */
        private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;
            // 从i开始，一直往下找，直到出现空的槽为止
            while (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    // 删除被jvm回收的对象
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }

        /**
         * 注意key是ThreadLocal就行
         */
        private void set(ThreadLocal<?> key, Object value) {
            /**
             * 我们不像get()那样使用快速路径，因为使用set()创建新条目至少和替换现有条目一样常见，在这种情况下，快速路径往往会失败。
             */
            Entry[] tab = table;
            int len = tab.length;
            // 因为len 是2的n次方，所以i的值是 [0,len-1]
            // 等价于 key.threadLocalHashCode % len, 不过位运算更高效
            int i = key.threadLocalHashCode & (len-1);
            // 从i开始，一直往后查询，替换（如果key相等）
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();

                if (k == key) {
                  // 替换掉旧值
                    e.value = value;
                    return;
                }
               // 和HashMap不一样，由于Entry
               // key继承了软引用，会出现k是null的情况！所以会接着在replaceStaleEntry重新循环寻找相同的key
              // 当出现null时，会调用replaceStaleEntry()方法接着循环寻找相同的key，如果存在，直接替换旧值。如果不存在，
              // 则在当前位置上重新创建新的Entry.
                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }
            // 找到null的槽时，需要新建一个Entry插入相应的位置
            tab[i] = new Entry(key, value);
            int sz = ++size;
            // 没找到新建一个Entry，都要检查数量是否超过临界值，如果超过，给hash表划分更多的空间
            if (!cleanSomeSlots(i, sz) && sz >= threshold)// 插入后判断是否需要扩容
                rehash();
        }

        /**
         * Remove the entry for key.
         */
        private void remove(ThreadLocal<?> key) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                if (e.get() == key) {
                    e.clear();
                    expungeStaleEntry(i);
                    return;
                }
            }
        }

        /**
         * 
         * 从staleSlot的下一位置开始查找待插入的key是否已经存在表中 若是，则更新其value值并将其与staleSlot位置处的元素交换
         * 否则，直接将其放置在staleSlot处。
         */
        private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                                       int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;

          /**
             * 备份以检查在当前运行中是否存在以前的陈旧条目。一次清除整个运行，以避免由于垃圾收集器释放引用而导致的连续增量重新哈希
             * (即每当收集器运行时)。
             */
            int slotToExpunge = staleSlot;
            for (int i = prevIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;

         /**
             * 查找运行的键或下一个空槽
             */
            for (int i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                    /**
                     * 如果我们找到键，那么我们需要用陈旧的条目来交换它，以保持哈希表的顺序。
                     */
                 if (k == key) {
                    e.value = value;

                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;

                    // 如果存在，则在以前的陈旧项处开始删除。
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // If we didn't find stale entry on backward scan, the
                // first stale entry seen while scanning for key is the
                // first still present in the run.
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }

            // If key not found, put new entry in stale slot
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);

            // If there are any other stale entries in run, expunge them
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        /**
         * 从指定位置staleSlot处开始清理失效元素
         * 
         * expungeStaleEntry的具体流程如下：先清理staleSlot位置处的元素，然后从staleSlot的下一位置开始查找，
         * 若遇到失效元素，则将其清理；若遇到合法元素，则对其进行rehash，调整其位置；若遇到空值，则循环退出。
         */
        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;

            // expunge entry at staleSlot
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;

            // Rehash until we encounter null
            Entry e;
            int i;
            for (i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                if (k == null) {
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        tab[i] = null;

                        // Unlike Knuth 6.4 Algorithm R, we must scan until
                        // null because multiple entries could have been stale.
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }

        /**
         * 从指定位置i开始，以log2(n)为窗口宽度，检查并清理失效元素。
         */
        private boolean cleanSomeSlots(int i, int n) {
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ( (n >>>= 1) != 0);
            return removed;
        }

        /**
         * rehash
         */
        private void rehash() {
            expungeStaleEntries();

            // Use lower threshold for doubling to avoid hysteresis
            // 使用较低的阈值加倍以避免迟滞。3/4
            if (size >= threshold - threshold / 4)
                resize();
        }

        /**
         * 翻倍
         */
        private void resize() {
            Entry[] oldTab = table;
            int oldLen = oldTab.length;
            int newLen = oldLen * 2;// 新建新的数组newTab，大小为原来的2倍。
            Entry[] newTab = new Entry[newLen];
            // 复制table的元素到newTab，忽略陈旧的元素，假设table中的元素e需要复制到newTab的i位置，如果i位置存在元素，则找下一个空位置进行插入。
            int count = 0;

            for (int j = 0; j < oldLen; ++j) {
                Entry e = oldTab[j];
                if (e != null) {
                    ThreadLocal<?> k = e.get();
                    if (k == null) {
                        e.value = null; // Help the GC
                    } else {
                        int h = k.threadLocalHashCode & (newLen - 1);
                        while (newTab[h] != null)
                            h = nextIndex(h, newLen);
                        newTab[h] = e;
                        count++;
                    }
                }
            }

            setThreshold(newLen);
            size = count;
            table = newTab;
        }

        /**
         * 删除表中的所有陈旧项。
         */
        private void expungeStaleEntries() {
            Entry[] tab = table;
            int len = tab.length;
            for (int j = 0; j < len; j++) {
                Entry e = tab[j];
                if (e != null && e.get() == null)
                    expungeStaleEntry(j);
            }
        }
    }
}
```

