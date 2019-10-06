---
title: LinkedHashMap
categories:
- Java源码
tags: [源码分析]
---

* 目录
{:toc}

### LinkedHashMap源码

```java
package java.util;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.io.IOException;

/**
 * LinkedHashMap是Map接口的哈希表与链表实现，同时继承了HashMap，这也意味着主要操作的实现使用的仍是HashMap的实现。而LinkedHashMap本身仅增加了自己的迭代器，结构比较简单，并且没有提供并发同步功能。
 * 需要并发的Map只能使用Concurrenthashmap，但是Concurrenthashmap不支持维持排序状态。ConcurrentLinkedHashMap是google团队提供的一个容器，它提供了多线程下同步集合的功能。
 * 对于LinkedHashMap：
 * 其内部迭代器均继承自LinkedHashIterator，拥有维持顺序的功能。拥有具有可预测的迭代顺序，它通过维护一个双链表，使得迭代是有序的。
 * 默认是以插入顺序构建有序链表，通过构造函数的accessOrder属性可以设置为以访问顺序或者以插入顺序。
 * 这个特性可以用在LRU缓冲中，在使用迭代器时若集合结构被修改也会抛出ConcurrentModificationException异常。
 * 若作为LRU缓存使用需要重写removeEldestEntry方法，然后LinkedHashMap才会拥有删除过期（老的）节点（元素）的能力
 *
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     HashMap
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.4
 */
public class LinkedHashMap<K,V>
    extends HashMap<K,V>
    implements Map<K,V>
{

    /*
     * Implementation note.  A previous version of this class was
     * internally structured a little differently. Because superclass
     * HashMap now uses trees for some of its nodes, class
     * LinkedHashMap.Entry is now treated as intermediary node class
     * that can also be converted to tree form. The name of this
     * class, LinkedHashMap.Entry, is confusing in several ways in its
     * current context, but cannot be changed.  Otherwise, even though
     * it is not exported outside this package, some existing source
     * code is known to have relied on a symbol resolution corner case
     * rule in calls to removeEldestEntry that suppressed compilation
     * errors due to ambiguous usages. So, we keep the name to
     * preserve unmodified compilability.
     *
     * The changes in node classes also require using two fields
     * (head, tail) rather than a pointer to a header node to maintain
     * the doubly-linked before/after list. This class also
     * previously used a different style of callback methods upon
     * access, insertion, and removal.
     */

    /**
     * 普通LinkedHashMap元素的节点子类，含有before、after的双向链表以维持集合的顺序。
     */
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }

    //serialVersionUID 表示可序列化类的版本，在反序列化对象时，用来确认序列化与反序列化该对象所使用的类的版本是否兼容。
    //如果类的版本不一致，那么反序列化将不能正常进行，抛出InvalidClassException，为防止不同计算算法不同，导致集合无法被反序列化，一般都显示指定这个值
    private static final long serialVersionUID = 3801124242820219131L;

    /**
     * 双链表的头
     */
    transient LinkedHashMap.Entry<K,V> head;

    /**
     * 双链表的尾（若accessOrder=true，作为LRU时，就是最后进入的，最新的结点）
     */
    transient LinkedHashMap.Entry<K,V> tail;

    /**
     * 控制集合迭代时的顺序，true=访问顺序，false=插入顺序
     * 可以称为 排序模式
     *
     * @serial
     */
    final boolean accessOrder;

    // internal utilities

    // 列表末尾的链接
    private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
        LinkedHashMap.Entry<K,V> last = tail;
        tail = p;
        if (last == null)
            head = p;
        else {
            p.before = last;
            last.after = p;
        }
    }

    // 将src的链接应用于dst
    private void transferLinks(LinkedHashMap.Entry<K,V> src,
                               LinkedHashMap.Entry<K,V> dst) {
        LinkedHashMap.Entry<K,V> b = dst.before = src.before;
        LinkedHashMap.Entry<K,V> a = dst.after = src.after;
        if (b == null)
            head = dst;
        else
            b.after = dst;
        if (a == null)
            tail = dst;
        else
            a.before = dst;
    }

    // 重写HashMap钩子方法，重置为初始默认状态。由clone和readobject调用
    void reinitialize() {
        super.reinitialize();
        head = tail = null;
    }

    Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
        LinkedHashMap.Entry<K,V> p =
            new LinkedHashMap.Entry<K,V>(hash, key, value, e);
        linkNodeLast(p);
        return p;
    }

    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
        LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
        LinkedHashMap.Entry<K,V> t =
            new LinkedHashMap.Entry<K,V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
        TreeNode<K,V> p = new TreeNode<K,V>(hash, key, value, next);
        linkNodeLast(p);
        return p;
    }

    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
        TreeNode<K,V> t = new TreeNode<K,V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    void afterNodeRemoval(Node<K,V> e) { // unlink
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.before = p.after = null;
        if (b == null)
            head = a;
        else
            b.after = a;
        if (a == null)
            tail = b;
        else
            a.before = b;
    }

    void afterNodeInsertion(boolean evict) { // 可能删除最旧的
        LinkedHashMap.Entry<K,V> first;
        if (evict && (first = head) != null && removeEldestEntry(first)) {
            K key = first.key;
            removeNode(hash(key), key, null, false, true);
        }
    }

    void afterNodeAccess(Node<K,V> e) { //将节点移到最后
        LinkedHashMap.Entry<K,V> last;
        //accessOrder为false表示以插入顺序排序的元素节点，不需要处理
        if (accessOrder && (last = tail) != e) {
            LinkedHashMap.Entry<K,V> p =
                (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
            p.after = null;
            if (b == null)
                head = a;
            else
                b.after = a;
            if (a != null)
                a.before = b;
            else
                last = b;
            if (last == null)
                head = p;
            else {
                p.before = last;
                last.after = p;
            }
            tail = p;
            ++modCount;
        }
    }

    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after) {
            s.writeObject(e.key);
            s.writeObject(e.value);
        }
    }

    /**
     * 能设置初始容量和平衡因子的构造函数
     *
     * @param  initialCapacity the initial capacity 
     * @param  loadFactor      the load factor 
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public LinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }

    /**
     * 能设置初始容量的构造函数
     *
     * @param  initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public LinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        accessOrder = false;
    }

    /**
     *
     * 无参构造，默认容量是16，平衡因子默认0.75
     */
    public LinkedHashMap() {
        super();
        accessOrder = false;
    }

    /**
     * 构造具有与指定集合相同值的（以插入顺序的）LinkedHashMap实例。
     * 使用默认的负载因子（0.75），并以传入的指定集合的大小作为初始化容量构造LinkedHashMap实例。
     * 
     * @param  m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public LinkedHashMap(Map<? extends K, ? extends V> m) {
        super();
        accessOrder = false;
        putMapEntries(m, false);
    }

    /**
     * 构造具有指定初始容量、加载因子和排序模式的空LinkedHashMap实例。
     * 这个构造函数是作为LRU缓冲时常用的
     * 
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @param  accessOrder     the ordering mode - <tt>true</tt> for
     *         access-order, <tt>false</tt> for insertion-order
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public LinkedHashMap(int initialCapacity,
                         float loadFactor,
                         boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }


    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(Object value) {
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after) {
            V v = e.value;
            if (v == value || (value != null && value.equals(v)))
                return true;
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     */
    public V get(Object key) {
        Node<K,V> e;
        if ((e = getNode(hash(key), key)) == null)
            return null;
        if (accessOrder)
            afterNodeAccess(e);
        return e.value;
    }

    /**
     * {@inheritDoc}
     */
    public V getOrDefault(Object key, V defaultValue) {
       Node<K,V> e;
       if ((e = getNode(hash(key), key)) == null)
           return defaultValue;
       if (accessOrder)
           afterNodeAccess(e);
       return e.value;
   }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        super.clear();
        head = tail = null;
    }

    /**
     * 如果此集合应删除其最老的节点，则返回 true，若是作为LRU缓存使用时可以通过删除过时节点来减少内存消耗
     * 同时，若LinkedHashMap需要作为LRU缓存时，一般会重写这个方法，并定义缓存中的元素的合适大小。超过这个大小值，集合会自动删除最老的元素
     * 这个默认的实现中永远反回false，表示最老的元素永远不会被移除。
     *
     * @param    eldest The least recently inserted entry in the map, or if
     *           this is an access-ordered map, the least recently accessed
     *           entry.  This is the entry that will be removed it this
     *           method returns <tt>true</tt>.  If the map was empty prior
     *           to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
     *           in this invocation, this will be the entry that was just
     *           inserted; in other words, if the map contains a single
     *           entry, the eldest entry is also the newest.
     * @return   <tt>true</tt> if the eldest entry should be removed
     *           from the map; <tt>false</tt> if it should be retained.
     */
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return false;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new LinkedKeySet();
            keySet = ks;
        }
        return ks;
    }

    final class LinkedKeySet extends AbstractSet<K> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<K> iterator() {
            return new LinkedKeyIterator();
        }
        public final boolean contains(Object o) { return containsKey(o); }
        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }
        public final Spliterator<K> spliterator()  {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED |
                                            Spliterator.DISTINCT);
        }
        public final void forEach(Consumer<? super K> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e.key);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * @return a view of the values contained in this map
     */
    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new LinkedValues();
            values = vs;
        }
        return vs;
    }

    final class LinkedValues extends AbstractCollection<V> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<V> iterator() {
            return new LinkedValueIterator();
        }
        public final boolean contains(Object o) { return containsValue(o); }
        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED);
        }
        public final void forEach(Consumer<? super V> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e.value);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new LinkedEntrySet()) : es;
    }

    final class LinkedEntrySet extends AbstractSet<Map.Entry<K,V>> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<Map.Entry<K,V>> iterator() {
            return new LinkedEntryIterator();
        }
        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            Node<K,V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }
        public final Spliterator<Map.Entry<K,V>> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED |
                                            Spliterator.DISTINCT);
        }
        public final void forEach(Consumer<? super Map.Entry<K,V>> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    // Map overrides

    public void forEach(BiConsumer<? super K, ? super V> action) {
        if (action == null)
            throw new NullPointerException();
        int mc = modCount;
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
            action.accept(e.key, e.value);
        if (modCount != mc)
            throw new ConcurrentModificationException();
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (function == null)
            throw new NullPointerException();
        int mc = modCount;
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
            e.value = function.apply(e.key, e.value);
        if (modCount != mc)
            throw new ConcurrentModificationException();
    }

    // Iterators
   //values keySet Entry 都继承该迭代器，也就是说，对于单个键或值或键值对本身的节点，都是使用迭代器来进行元素遍历的。
   //它们都有继承父类的modCount属性，都可能出现ConcurrentModificationException异常

    abstract class LinkedHashIterator {
        LinkedHashMap.Entry<K,V> next;
        LinkedHashMap.Entry<K,V> current;
        int expectedModCount;

        LinkedHashIterator() {
            next = head;
            expectedModCount = modCount;
            current = null;
        }

        public final boolean hasNext() {
            return next != null;
        }

        final LinkedHashMap.Entry<K,V> nextNode() {
            LinkedHashMap.Entry<K,V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            current = e;
            next = e.after;
            return e;
        }

        public final void remove() {
            Node<K,V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class LinkedKeyIterator extends LinkedHashIterator
        implements Iterator<K> {
        public final K next() { return nextNode().getKey(); }
    }

    final class LinkedValueIterator extends LinkedHashIterator
        implements Iterator<V> {
        public final V next() { return nextNode().value; }
    }

    final class LinkedEntryIterator extends LinkedHashIterator
        implements Iterator<Map.Entry<K,V>> {
        public final Map.Entry<K,V> next() { return nextNode(); }
    }


}
```

### LinkedHashMap在LRU中的使用

```scala
import java.{ util => ju }

import scala.collection.JavaConverters._

/**
 * this lru-cache use LinkedHashMap don't resolve ConcurrentModificationException
 * 1. use local var to save values when put in other's threads
 * 2. use google's ConcurrentLinkedHashMap
 *
 * @author 梦境迷离
 * @since 2019-09-28
 * @version v1.0
 */
class LRUCache[K, V](val maxSize: Int, underlying: ju.Map[K, V]) {

  def put(key: K, value: V): Unit = {
    this.underlying.put(key, value)
  }

  def get(key: K): Option[V] = {
    Option(this.underlying.get(key))
  }

  //can't use it  when put in other's threads
  def values(): Seq[V] = {
    this.underlying.values().asScala.toSeq
  }

  def contains(key: K): Boolean = {
    this.underlying.containsKey(key)
  }

  def batchPut(values: Seq[(K, V)]): Unit = {
    values.foreach {
      case (key, value) =>
        put(key, value)
    }
  }

  def clear(): Unit = {
    this.underlying.clear()
  }
}

object LRUCache {

  //use it to create a cache
  def apply[K, V](maxSize: Int) = new LRUCache(maxSize, LRUCache.makeUnderlying[K, V](maxSize))

  // initial capacity and load factor are the normal defaults for LinkedHashMap
  def makeUnderlying[K, V](maxSize: Int): ju.Map[K, V] =
    new ju.LinkedHashMap[K, V](
      maxSize, /* initial capacity */
      0.75f, /* load factor */
      true /* access order (as opposed to insertion order) */
    ) {
      override protected def removeEldestEntry(eldest: ju.Map.Entry[K, V]): Boolean = {
        this.size() > maxSize
      }
    }
}
```
