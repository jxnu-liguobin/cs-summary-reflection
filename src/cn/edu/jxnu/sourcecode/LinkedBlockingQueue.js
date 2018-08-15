package java.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * 
 * 无界，链式，阻塞，线程安全的队列
 * 
 * @since 1.5
 * @author Doug Lea
 * @param <E>
 *            the type of elements held in this collection
 */
public class LinkedBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {
    private static final long serialVersionUID = -6903933977591709194L;

    /**
	 * 队列节点数据结构
	 */
    static class Node<E> {
        E item;

        /**
		 * One of: - the real successor Node - this Node, meaning the successor
		 * is head.next - null, meaning there is no successor (this is the last
		 * node)
		 */
        Node<E> next;

        Node(E x) { item = x; }
    }

    /**
	 * LinkedBlockingQueue构造的时候若没有指定大小，则默认大小为Integer.MAX_VALUE，当然也可以在构造函数的参数中指定大小。LinkedBlockingQueue不接受null。
	 */
    private final int capacity;

    /** 当前元素的数量，线程安全的 */
    private final AtomicInteger count = new AtomicInteger();

    /**
	 * Head of linked list. Invariant: head.item == null 头不存东西
	 */
    transient Node<E> head;

    /**
	 * Tail of linked list. Invariant: last.next == null 尾指向空
	 */
    private transient Node<E> last;

    /** take, poll, 操作的锁 */
    private final ReentrantLock takeLock = new ReentrantLock();

    /** 是一个锁定状态,take,poll 需要判断是否为空， */
    private final Condition notEmpty = takeLock.newCondition();

    /** put, offer, 操作的锁 */
    private final ReentrantLock putLock = new ReentrantLock();

    /** 链队一般不会满，这里是指阻塞 */
    private final Condition notFull = putLock.newCondition();

    /**
	 * Signals a waiting take. Called only from put/offer (which do not
	 * otherwise ordinarily lock takeLock.)
	 * 
	 * 表示唤醒take/poll等待,只能在put/offer中被调用,也就是元素入队后,可以通过这个来通知队列，不再是空了
	 */
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
	 * Signals a waiting put. Called only from take/poll.
	 * 
	 * 表示唤醒put/offer等待,只能在take/poll中被调用,也就是出队,可以通过这个方法通知队列不再是满状态了
	 */
    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
        	// 唤醒
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    /**
	 * 内部方法，向队列尾加入节点
	 * 
	 * @param node
	 *            the node
	 */
    private void enqueue(Node<E> node) {
        // assert putLock.isHeldByCurrentThread();
        // assert last.next == null;
        last = last.next = node;
    }

    /**
	 * 
	 * 内部方法，从队列头移除一个元素
	 * 
	 * @return the node
	 */
    private E dequeue() {
        // assert takeLock.isHeldByCurrentThread();
        // assert head.item == null;
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h; // help GC
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    /**
	 * 锁定，以防止元素出队
	 */
    void fullyLock() {
        putLock.lock();
        takeLock.lock();
    }

    /**
	 * 解锁，以允许元素入队
	 */
    void fullyUnlock() {
        takeLock.unlock();
        putLock.unlock();
    }

// /**
// * Tells whether both locks are held by current thread.
// */
// boolean isFullyLocked() {
// return (putLock.isHeldByCurrentThread() &&
// takeLock.isHeldByCurrentThread());
// }

    /**
	 * 默认构造
	 */
    public LinkedBlockingQueue() {
    	// 可见默认队列大写就是MAX_VALUE
        this(Integer.MAX_VALUE);
    }

    /**
	 * 可以指定大小的
	 */
    public LinkedBlockingQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();// 不可以小于0，直接抛异常
        this.capacity = capacity;
        last = head = new Node<E>(null);
    }

    /**
	 * 通过先有实现Collection接口的集合构造阻塞队列
	 */
    public LinkedBlockingQueue(Collection<? extends E> c) {
        this(Integer.MAX_VALUE);// 调用默认构造
        final ReentrantLock putLock = this.putLock;
        putLock.lock(); // 从未发生竞争，但是对于可见性来说是有必要的，很明显，此时队列不可能满
        try {
            int n = 0;
            for (E e : c) {// 遍历c集合
                if (e == null)
                    throw new NullPointerException();// 不能存空值
                if (n == capacity)
                    throw new IllegalStateException("Queue full");
                enqueue(new Node<E>(e));// 从尾入队
                ++n;
            }
            count.set(n);// 修改元素个数
        } finally {
            putLock.unlock();// 释放入队锁定
        }
    }

    /**
	 * 返回队列中元素的个数
	 */
    public int size() {
        return count.get();
    }

    /**
	 * 返回可用，容量== 初始容量-现有元素个数，不能通过该方法判断是否入队成功，因为可能被其他线程修改，简单来说就是不精确不可靠的方法
	 */
    public int remainingCapacity() {
        return capacity - count.get();
    }

    /**
	 * 将指定的元素插入到此队列的尾部，如果阻塞，则等待空间变得可用。
	 * 
	 * @throws InterruptedException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 */
    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        // Note: convention in all put/take/etc is to preset local var
        // holding count negative to indicate failure unless set.
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            /*
			 * 
			 * 直到队列可用【链队如果不设置默认值，就是MAX，几乎用不完】
			 */
            while (count.get() == capacity) {
                notFull.await();
            }
            enqueue(node);// 进行尾入队
            c = count.getAndIncrement();// 计数=元素的个数，然后再+1
            if (c + 1 < capacity)
                notFull.signal();// 加1后再判断与初始化的容量大小，如果小，则发出唤醒队列【非满】操作
        } finally {
            putLock.unlock();
        }
        if (c == 0) // 前面getandset操作，导致只要前面进行成功，后面c必须等于0，而-1表示失败
            signalNotEmpty(); // 通知队列不再是空，可以进行下一步的take,poll操作
    }

    /**
	 * 在这个队列的尾部插入指定的元素，如果阻塞，等待指定的时间，直到空间可用
	 * 
	 * @return {@code true} if successful, or {@code false} if the specified
	 *         waiting time elapses before space is available
	 * @throws InterruptedException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 */
    public boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException {

        if (e == null) throw new NullPointerException();
        long nanos = unit.toNanos(timeout);// 获得纳秒数
        int c = -1;
        final ReentrantLock putLock = this.putLock;// 得到写入锁
        final AtomicInteger count = this.count;// 得到当前元素个数
        putLock.lockInterruptibly();// 优先响应中断，而不是响应锁的重入和获取
        try {
            while (count.get() == capacity) {
            	// 满队
                if (nanos <= 0)
                    return false;// 小于0即不允许等待，直接返回false
                nanos = notFull.awaitNanos(nanos);// 在满队状态上发起指定时间的等待
            }
            enqueue(new Node<E>(e));// 进行队尾入队
            c = count.getAndIncrement();// 当前元素个数+1
            if (c + 1 < capacity)
                notFull.signal();// 判断是否在加了这个元素之后，变成满队
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();// 唤醒元素非空【第一次添加元素的时候生效】
        return true;
    }

    /**
	 * 如果立即可行,插入,失败返回false,而不是抛出异常
	 */
    public boolean offer(E e) {
        if (e == null) throw new NullPointerException();
        final AtomicInteger count = this.count;
        if (count.get() == capacity)// 如果是满，直接返回false
            return false;
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;// 得到写入锁
        putLock.lock();
        try {
            if (count.get() < capacity) {// 如果小于容量，进行队尾入队
                enqueue(node);
                c = count.getAndIncrement();
                if (c + 1 < capacity)
                    notFull.signal();// 唤醒可能存在的满队列上的等待
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();// 唤醒可能存在的空队列上的等待
        return c >= 0;
    }

    /**
	 * 获取/出队
	 * 
	 * 可响应中断
	 */
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
            	// 空队，进行等待
                notEmpty.await();
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }

    /**
	 * 出队，阻塞
	 * 
	 * 可响应中断
	 */
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E x = null;
        int c = -1;
        long nanos = unit.toNanos(timeout);
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                if (nanos <= 0)
                    return null;// 如果为空且不允许等待，则直接返回null
                nanos = notEmpty.awaitNanos(nanos);// 否则进行指定时间的等待
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }

    /**
	 * 出队，无参数的，同理，不可响应中断
	 */
    public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0)
            return null;
        E x = null;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count.get() > 0) {
                x = dequeue();
                c = count.getAndDecrement();
                if (c > 1)
                    notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }

    /**
	 * 获取队首元素，但不删除原元素
	 */
    public E peek() {
        if (count.get() == 0)
            return null;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            Node<E> first = head.next;
            if (first == null)
                return null;// 不存在首，返回null
            else
                return first.item;// 返回首元素
        } finally {
            takeLock.unlock();
        }
    }

    /**
	 * 将P从trail链上断开
	 */
    void unlink(Node<E> p, Node<E> trail) {
        // assert isFullyLocked();
        // p.next is not changed, to allow iterators that are
        // traversing p to maintain their weak-consistency guarantee.
        p.item = null;
        trail.next = p.next;
        if (last == p)
            last = trail;
        if (count.getAndDecrement() == capacity)
            notFull.signal();
    }

    /**
	 * 从队列删除元素
	 * 
	 * 返回true/false
	 */
    public boolean remove(Object o) {
        if (o == null) return false;// 空返回false
        fullyLock();
        try {
            for (Node<E> trail = head, p = trail.next;
                 p != null;
                 trail = p, p = p.next) {
                if (o.equals(p.item)) {
                    unlink(p, trail);
                    return true;
                }
            }
            return false;
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * 判断队列是否含有元素
	 * 
	 * true/false
	 */
    public boolean contains(Object o) {
        if (o == null) return false;// 空返回false
        fullyLock();
        try {
            for (Node<E> p = head.next; p != null; p = p.next)
                if (o.equals(p.item))
                    return true;
            return false;
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * 将队列转换为数组
	 */
    public Object[] toArray() {
        fullyLock();
        try {
            int size = count.get();
            Object[] a = new Object[size];// new一个元素大小的对象数组
            int k = 0;
            for (Node<E> p = head.next; p != null; p = p.next)
                a[k++] = p.item;
            return a;
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * 转换为数组
	 */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        fullyLock();
        try {
            int size = count.get();
            if (a.length < size)
                a = (T[])java.lang.reflect.Array.newInstance
                    (a.getClass().getComponentType(), size);

            int k = 0;
            for (Node<E> p = head.next; p != null; p = p.next)
                a[k++] = (T)p.item;
            if (a.length > k)
                a[k] = null;
            return a;
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * 默认实现的toString方法
	 */
    public String toString() {
        fullyLock();
        try {
            Node<E> p = head.next;
            if (p == null)
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (;;) {
                E e = p.item;
                sb.append(e == this ? "(this Collection)" : e);
                p = p.next;
                if (p == null)
                    return sb.append(']').toString();
                sb.append(',').append(' ');
            }
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * 
	 * 清空队列
	 */
    public void clear() {
        fullyLock();
        try {
            for (Node<E> p, h = head; (p = h.next) != null; h = p) {
                h.next = h;
                p.item = null;
            }
            head = last;
            // assert head.item == null && head.next == null;
            if (count.getAndSet(0) == capacity)
                notFull.signal();
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * @throws UnsupportedOperationException
	 *             {@inheritDoc}
	 * @throws ClassCastException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 * @throws IllegalArgumentException
	 *             {@inheritDoc}
	 */
    public int drainTo(Collection<? super E> c) {
        return drainTo(c, Integer.MAX_VALUE);
    }

    /**
	 * 
	 * 一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），
	 * 通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
	 */
    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null)
            throw new NullPointerException();// 最大个数为空，抛出异常
        if (c == this)
            throw new IllegalArgumentException();// 非本对象，抛出异常
        if (maxElements <= 0)// 最大个数小于0 ，返回0
            return 0;
        boolean signalNotFull = false;
        final ReentrantLock takeLock = this.takeLock;// 加上读锁
        takeLock.lock();
        try {
            int n = Math.min(maxElements, count.get()); // 获取最先可用的元素个数
            // count.get provides visibility to first n Nodes
            Node<E> h = head;
            int i = 0;
            try {
                while (i < n) {
                    Node<E> p = h.next;
                    c.add(p.item);
                    p.item = null;
                    h.next = h;
                    h = p;
                    ++i;
                }
                return n;
            } finally {
            	// 如果add抛出了异常。则进行恢复
                if (i > 0) {
                    // assert h.item == null;
                    head = h;
                    signalNotFull = (count.getAndAdd(-i) == capacity);
                }
            }
        } finally {
            takeLock.unlock();
            if (signalNotFull)
                signalNotFull();
        }
    }

    /**
	 * Returns an iterator over the elements in this queue in proper sequence.
	 * The elements will be returned in order from first (head) to last (tail).
	 * 
	 * <p>
	 * The returned iterator is <a href="package-summary.html#Weakly"><i>weakly
	 * consistent</i></a>.
	 * 
	 * @return an iterator over the elements in this queue in proper sequence
	 */
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        /*
		 * Basic weakly-consistent iterator. At all times hold the next item to
		 * hand out so that if hasNext() reports true, we will still have it to
		 * return even if lost race with a take etc.
		 */

        private Node<E> current;
        private Node<E> lastRet;
        private E currentElement;

        Itr() {
            fullyLock();
            try {
                current = head.next;
                if (current != null)
                    currentElement = current.item;
            } finally {
                fullyUnlock();
            }
        }

        public boolean hasNext() {
            return current != null;
        }

        /**
		 * Returns the next live successor of p, or null if no such.
		 * 
		 * Unlike other traversal methods, iterators need to handle both: -
		 * dequeued nodes (p.next == p) - (possibly multiple) interior removed
		 * nodes (p.item == null)
		 */
        private Node<E> nextNode(Node<E> p) {
            for (;;) {
                Node<E> s = p.next;
                if (s == p)
                    return head.next;
                if (s == null || s.item != null)
                    return s;
                p = s;
            }
        }

        public E next() {
            fullyLock();
            try {
                if (current == null)
                    throw new NoSuchElementException();
                E x = currentElement;
                lastRet = current;
                current = nextNode(current);
                currentElement = (current == null) ? null : current.item;
                return x;
            } finally {
                fullyUnlock();
            }
        }

        public void remove() {
            if (lastRet == null)
                throw new IllegalStateException();
            fullyLock();
            try {
                Node<E> node = lastRet;
                lastRet = null;
                for (Node<E> trail = head, p = trail.next;
                     p != null;
                     trail = p, p = p.next) {
                    if (p == node) {
                        unlink(p, trail);
                        break;
                    }
                }
            } finally {
                fullyUnlock();
            }
        }
    }

    /** A customized variant of Spliterators.IteratorSpliterator */
    static final class LBQSpliterator<E> implements Spliterator<E> {
        static final int MAX_BATCH = 1 << 25;  // max batch array size;
        final LinkedBlockingQueue<E> queue;
        Node<E> current;    // current node; null until initialized
        int batch;          // batch size for splits
        boolean exhausted;  // true when no more nodes
        long est;           // size estimate
        LBQSpliterator(LinkedBlockingQueue<E> queue) {
            this.queue = queue;
            this.est = queue.size();
        }

        public long estimateSize() { return est; }

        public Spliterator<E> trySplit() {
            Node<E> h;
            final LinkedBlockingQueue<E> q = this.queue;
            int b = batch;
            int n = (b <= 0) ? 1 : (b >= MAX_BATCH) ? MAX_BATCH : b + 1;
            if (!exhausted &&
                ((h = current) != null || (h = q.head.next) != null) &&
                h.next != null) {
                Object[] a = new Object[n];
                int i = 0;
                Node<E> p = current;
                q.fullyLock();
                try {
                    if (p != null || (p = q.head.next) != null) {
                        do {
                            if ((a[i] = p.item) != null)
                                ++i;
                        } while ((p = p.next) != null && i < n);
                    }
                } finally {
                    q.fullyUnlock();
                }
                if ((current = p) == null) {
                    est = 0L;
                    exhausted = true;
                }
                else if ((est -= i) < 0L)
                    est = 0L;
                if (i > 0) {
                    batch = i;
                    return Spliterators.spliterator
                        (a, 0, i, Spliterator.ORDERED | Spliterator.NONNULL |
                         Spliterator.CONCURRENT);
                }
            }
            return null;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            if (action == null) throw new NullPointerException();
            final LinkedBlockingQueue<E> q = this.queue;
            if (!exhausted) {
                exhausted = true;
                Node<E> p = current;
                do {
                    E e = null;
                    q.fullyLock();
                    try {
                        if (p == null)
                            p = q.head.next;
                        while (p != null) {
                            e = p.item;
                            p = p.next;
                            if (e != null)
                                break;
                        }
                    } finally {
                        q.fullyUnlock();
                    }
                    if (e != null)
                        action.accept(e);
                } while (p != null);
            }
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null) throw new NullPointerException();
            final LinkedBlockingQueue<E> q = this.queue;
            if (!exhausted) {
                E e = null;
                q.fullyLock();
                try {
                    if (current == null)
                        current = q.head.next;
                    while (current != null) {
                        e = current.item;
                        current = current.next;
                        if (e != null)
                            break;
                    }
                } finally {
                    q.fullyUnlock();
                }
                if (current == null)
                    exhausted = true;
                if (e != null) {
                    action.accept(e);
                    return true;
                }
            }
            return false;
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.NONNULL |
                Spliterator.CONCURRENT;
        }
    }

    /**
	 * Returns a {@link Spliterator} over the elements in this queue.
	 * 
	 * <p>
	 * The returned spliterator is <a href="package-summary.html#Weakly"><i>weakly
	 * consistent</i></a>.
	 * 
	 * <p>
	 * The {@code Spliterator} reports {@link Spliterator#CONCURRENT},
	 * {@link Spliterator#ORDERED}, and {@link Spliterator#NONNULL}.
	 * 
	 * @implNote The {@code Spliterator} implements {@code trySplit} to permit
	 *           limited parallelism.
	 * 
	 * @return a {@code Spliterator} over the elements in this queue
	 * @since 1.8
	 */
    public Spliterator<E> spliterator() {
        return new LBQSpliterator<E>(this);
    }

    /**
	 * Saves this queue to a stream (that is, serializes it).
	 * 
	 * @param s
	 *            the stream
	 * @throws java.io.IOException
	 *             if an I/O error occurs
	 * @serialData The capacity is emitted (int), followed by all of its
	 *             elements (each an {@code Object}) in the proper order,
	 *             followed by a null
	 */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {

        fullyLock();
        try {
            // Write out any hidden stuff, plus capacity
            s.defaultWriteObject();

            // Write out all elements in the proper order.
            for (Node<E> p = head.next; p != null; p = p.next)
                s.writeObject(p.item);

            // Use trailing null as sentinel
            s.writeObject(null);
        } finally {
            fullyUnlock();
        }
    }

    /**
	 * Reconstitutes this queue from a stream (that is, deserializes it).
	 * 
	 * @param s
	 *            the stream
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object could not be found
	 * @throws java.io.IOException
	 *             if an I/O error occurs
	 */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in capacity, and any hidden stuff
        s.defaultReadObject();

        count.set(0);
        last = head = new Node<E>(null);

        // Read in all elements and place in queue
        for (;;) {
            @SuppressWarnings("unchecked")
            E item = (E)s.readObject();
            if (item == null)
                break;
            add(item);
        }
    }
}
