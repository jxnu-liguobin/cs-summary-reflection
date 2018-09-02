```java
package java.util.concurrent.locks;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;

/**
* 1 如果读取执行情况很多，写入很少的情况下，使用 ReentrantReadWriteLock 可能会使写入线程遭遇饥饿（Starvation）问题
* 也就是写入线程迟迟无法竞争到锁定而一直处于等待状态。
* 
  2 StampedLock控制锁有三种模式（写，读，乐观读），一个StampedLock状态是由版本和模式两个部分组成，锁获取方法返回一个数字作为票据stamp
  它用相应的锁状态表示并控制访问，数字0表示没有写锁被授权访问。在读锁上分为悲观锁和乐观锁 
  
  3 该类是一个读写锁的改进，它的思想是读写锁中读不仅不阻塞读，同时也不应该阻塞写。 
  
  乐观读不阻塞写的实现思路：
   
  在乐观读的时候如果发生了写，则应当重读而不是在读的时候直接阻塞写！ 
  因为在读线程非常多而写线程比较少的情况下，写线程可能发生饥饿现象，也就是因为大量的读线程存在并且读线程都阻塞写线程，
  因此写线程可能几乎很少被调度成功！当读执行的时候另一个线程执行了写，则读线程发现数据不一致则执行重读即可。所以读写都存在的情况下
  使用StampedLock就可以实现一种无障碍操作，即读写之间不会阻塞对方，但是写和写之间还是阻塞的！
  
  4 适用场景： 
  
  乐观读取模式仅用于短时间读取操作时经常能够降低竞争和提高吞吐量。当然，它的使用在本质上是脆弱的。乐观读取的区域应该只包括字段
  并且在validation之后用局部变量持有它们从而在后续使用。 
  乐观模式下读取的字段值很可能是非常不一致的，所以它应该只用于那些你熟悉如何展示数据，从而你可以不断检查一致性和调用方法validate
  
  5 优化点： 
  
  a 乐观读不阻塞悲观读和写操作，有利于获得写锁 
  
  b 队列头结点采用有限次数SPINS次自旋（增加开销），增加获得锁几率（因为闯入的线程会竞争锁），有效够降低上下文切换 
  
  c 读模式的集合通过一个公共节点被聚集在一起（cowait链），当队列尾节点为RMODE,通过CAS方法将该节点node添加至尾节点的cowait链中
  node成为cowait中的顶元素，cowait构成了一个LIFO队列。 
  当队列尾节点为WMODE，当前节点直接拼接到尾节点后面，保证了相对较公平的锁。 
  
  d 不支持锁重入，如果只悲观读锁和写锁，效率没有ReentrantReadWriteLock高。
*/
public class StampedLock implements java.io.Serializable {

    /**
    * 如果没有显示指定序列号，则JVM生成默认序列号，修改类将导致每次生成的序列号不同，类在未来无法完成反序列操作，
    * 而显示指定的序列号可以在未来修改类，不影响
    */
    private static final long serialVersionUID = -6001602636862214147L;

    /** 获取服务器CPU核数 */
    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    /** 线程入队列前自旋次数 */
    private static final int SPINS = (NCPU > 1) ? 1 << 6 : 0;

    /** 队列头结点自旋获取锁最大失败次数后再次进入队列 */
    private static final int HEAD_SPINS = (NCPU > 1) ? 1 << 10 : 0;

    /** 重新阻塞之前的最大重试次数 */
    private static final int MAX_HEAD_SPINS = (NCPU > 1) ? 1 << 16 : 0;

    /** The period for yielding when waiting for overflow spinlock */
    private static final int OVERFLOW_YIELD_RATE = 7; // must be power 2 - 1

    /** 溢出之前用于阅读器计数的位数 */
    private static final int LG_READERS = 7;

    // 锁定状态和stamp操作的值
    private static final long RUNIT = 1L;
    private static final long WBIT  = 1L << LG_READERS;
    private static final long RBITS = WBIT - 1L;
    private static final long RFULL = RBITS - 1L;
    private static final long ABITS = RBITS | WBIT;   //前8位都为1
    private static final long SBITS = ~RBITS; // 1 1000 0000

    //锁state初始值，第9位为1，避免算术时和0冲突
    private static final long ORIGIN = WBIT << 1;

    // 来自取消获取方法的特殊值，因此调用者可以抛出IE
    private static final long INTERRUPTED = 1L;

    // WNode节点的status值
    private static final int WAITING   = -1;
    private static final int CANCELLED =  1;

    // WNode节点的读写模式
    private static final int RMODE = 0;
    private static final int WMODE = 1;

    /** Wait nodes */
    static final class WNode {
    volatile WNode prev;
    volatile WNode next;
    volatile WNode cowait;    // 读模式使用该节点形成栈
    volatile Thread thread;   // non-null while possibly parked
    volatile int status;      // 0, WAITING, or CANCELLED
    final int mode;           // RMODE or WMODE
    WNode(int m, WNode p) { mode = m; prev = p; }
}

/** CLH队头节点 */
private transient volatile WNode whead;
/** CLH队尾节点 */
private transient volatile WNode wtail;
/**
* StampedLockd源码中的WNote就是等待链表队列，每一个WNode标识一个等待线程，whead为CLH队列头，wtail为CLH队列尾，state为锁的状态。
* long型即64位，倒数第八位标识写锁状态，如果为1，标识写锁占用！下面围绕这个state来讲述锁操作。
  首先是常量标识：
  WBIT=1000 0000（即-128）
  RBIT =0111 1111（即127） 
  SBIT =1000 0000（后7位表示当前正在读取的线程数量，清0）
   
*/

// views
transient ReadLockView readLockView;
transient WriteLockView writeLockView;
transient ReadWriteLockView readWriteLockView;

/** 锁队列状态， 当处于写模式时第8位为1，读模式时前7位为1-126（附加的readerOverflow用于当读者超过126时） */
private transient volatile long state;
/** 将state超过 RFULL=126的值放到readerOverflow字段中 */
private transient int readerOverflow;

/**
 * 初始化state=ORIGIN.
 */
public StampedLock1() {
    state = ORIGIN;
}

/**
 *
 *获取写锁，获取失败会一直阻塞，直到获得锁成功
 * @return 可以用来解锁或转换模式的戳记(128的整数)
 */
public long writeLock() {
    long s, next;
    return ((((s = state) & ABITS) == 0L &&   // 完全没有任何锁（没有读锁和写锁）的时候可以通过
    U.compareAndSwapLong(this, STATE, s, next = s + WBIT)) ? //第8位置为1
        next : acquireWrite(false, 0L));
}

/**
 * 没有任何锁时则获取写锁，否则返回0
 *
 * @return 可以用来解锁或转换模式的戳记(128的整数),获取失败返回0
 */
public long tryWriteLock() {
    long s, next;
    return ((((s = state) & ABITS) == 0L &&
    U.compareAndSwapLong(this, STATE, s, next = s + WBIT)) ?
        next : 0L);
}

/**
 * unit时间内获得写锁成功返回状态值，失败返回0，或抛出InterruptedException
 * @return 0：获得锁失败
 * @throws InterruptedException 线程获得锁之前调用interrupt()方法抛出的异常
 */
public long tryWriteLock(long time, TimeUnit unit)
throws InterruptedException {
    long nanos = unit.toNanos(time);
    if (!Thread.interrupted()) {
        long next, deadline;
        if ((next = tryWriteLock()) != 0L)
        //获得锁成功
        return next;
        if (nanos <= 0L)
        //超时返回0
        return 0L;
        if ((deadline = System.nanoTime() + nanos) == 0L)
        deadline = 1L;
        if ((next = acquireWrite(true, deadline)) != INTERRUPTED)
        //规定时间内获得锁结果
            return next;
    }
    throw new InterruptedException();
}

/**
 * 获得写锁成功返回状态值，失败返回0，或抛出InterruptedException
 */
public long writeLockInterruptibly() throws InterruptedException {
    long next;
    if (!Thread.interrupted() &&
        (next = acquireWrite(true, 0L)) != INTERRUPTED)
    return next;
    throw new InterruptedException();
}

/**
 *  悲观读锁，非独占锁，为获得锁一直处于阻塞状态，直到获得锁为止
 */
public long readLock() {
    long s = state, next;
    // 队列为空   && 没有写锁同时读锁数小于126  && CAS修改状态成功      则状态加1并返回，否则自旋获取读锁
    return ((whead == wtail && (s & ABITS) < RFULL &&
        U.compareAndSwapLong(this, STATE, s, next = s + RUNIT)) ?
        next : acquireRead(false, 0L));
}

/**
 * 可以立即获得锁，则获取读锁，否则返回0
 */
public long tryReadLock() {
    for (;;) {
        long s, m, next;
        //持有写锁返回0
        if ((m = (s = state) & ABITS) == WBIT)
            return 0L;
        //读线程数 < RFULL,CAS变更状态
    else if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, next = s + RUNIT))
                return next;
        }
        //将state超过 RFULL的值放到readerOverflow字段
        else if ((next = tryIncReaderOverflow(s)) != 0L)
        return next;
    }
}

/**
 * unit时间内获得读锁成功返回状态值，失败返回0，或抛出InterruptedException
 */
public long tryReadLock(long time, TimeUnit unit)
throws InterruptedException {
    long s, m, next, deadline;
    long nanos = unit.toNanos(time);
    if (!Thread.interrupted()) {
        if ((m = (s = state) & ABITS) != WBIT) {
            if (m < RFULL) {
                if (U.compareAndSwapLong(this, STATE, s, next = s + RUNIT))
                    return next;
            }
            else if ((next = tryIncReaderOverflow(s)) != 0L)
            return next;
        }
        if (nanos <= 0L)
        return 0L;
        if ((deadline = System.nanoTime() + nanos) == 0L)
        deadline = 1L;
        if ((next = acquireRead(true, deadline)) != INTERRUPTED)
            return next;
    }
    throw new InterruptedException();
}

public long readLockInterruptibly() throws InterruptedException {
    long next;
    if (!Thread.interrupted() &&
        (next = acquireRead(true, 0L)) != INTERRUPTED)
    return next;
    throw new InterruptedException();
}

/**
 * 获取乐观读锁，返回邮票stamp
 */
public long tryOptimisticRead() {
    long s;  //有写锁返回0.   否则返回256
    return (((s = state) & WBIT) == 0L) ? (s & SBITS) : 0L;
}

/**
 * 验证从调用tryOptimisticRead开始到现在这段时间内有无写锁占用过锁资源，有写锁获得过锁资源则返回false. stamp为0返回false.
 * @return 从返回stamp开始，没有写锁获得过锁资源返回true，否则返回false
 */
public boolean validate(long stamp) {
    //强制读取操作和验证操作在一些情况下的内存排序问题
    U.loadFence();
    //当持有写锁后再释放写锁，该校验也不成立，返回false
    return (stamp & SBITS) == (state & SBITS);
}

/**
 * state匹配stamp则释放写锁，
 * @throws IllegalMonitorStateException  不匹配则抛出异常
 */
public void unlockWrite(long stamp) {
    WNode h;
    //state不匹配stamp  或者 没有写锁
    if (state != stamp || (stamp & WBIT) == 0L)
    throw new IllegalMonitorStateException();
    //state += WBIT， 第8位置为0，但state & SBITS 会循环，一共有4个值
    state = (stamp += WBIT) == 0L ? ORIGIN : stamp;
    if ((h = whead) != null && h.status != 0)
    //唤醒继承者节点线程
        release(h);
}

/**
 * state匹配stamp则释放读锁，
 */
public void unlockRead(long stamp) {
    long s, m; WNode h;
    for (;;) {
        //不匹配抛出异常
        if (((s = state) & SBITS) != (stamp & SBITS) ||
            (stamp & ABITS) == 0L || (m = s & ABITS) == 0L || m == WBIT)
        throw new IllegalMonitorStateException();
        //小于最大记录数值
        if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, s - RUNIT)) {
                if (m == RUNIT && (h = whead) != null && h.status != 0)
                    release(h);
                break;
            }
        }
        //否则readerOverflow减一
        else if (tryDecReaderOverflow(s) != 0L)
        break;
    }
}

/**
 * state匹配stamp时，释放一个读锁或写锁
 * @throws IllegalMonitorStateException 如果state不匹配stamp
 */
public void unlock(long stamp) {
    long a = stamp & ABITS, m, s; WNode h;
    //有锁，state匹配stamp
    while (((s = state) & SBITS) == (stamp & SBITS)) {
        //初始状态
        if ((m = s & ABITS) == 0L)
        break;
        //写锁
    else if (m == WBIT) {
            if (a != m)
                break;
            state = (s += WBIT) == 0L ? ORIGIN : s;
            if ((h = whead) != null && h.status != 0)
                release(h);
            return;
        }
        //表示没有锁
        else if (a == 0L || a >= WBIT)
        break;
        //读锁
    else if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, s - RUNIT)) {
                if (m == RUNIT && (h = whead) != null && h.status != 0)
                    release(h);
                return;
            }
        }
        else if (tryDecReaderOverflow(s) != 0L)
        return;
    }
    throw new IllegalMonitorStateException();
}

/**
 * state匹配stamp时, 执行下列操作之一.
 1、stamp 已经持有写锁，直接返回.
 2、读模式，但是没有更多的读取者，并返回一个写锁stamp.
 3、有一个乐观读锁，只在即时可用的前提下返回一个写锁stamp
 4、其他情况都返回0
 */
public long tryConvertToWriteLock(long stamp) {
    long a = stamp & ABITS, m, s, next;
    //state匹配stamp
    while (((s = state) & SBITS) == (stamp & SBITS)) {
        //没有锁
        if ((m = s & ABITS) == 0L) {
            if (a != 0L)
            break;
            //CAS修改状态为持有写锁，并返回
            if (U.compareAndSwapLong(this, STATE, s, next = s + WBIT))
                return next;
        }
        //持有写锁
    else if (m == WBIT) {
            if (a != m)
            //其他线程持有写锁
                break;
            //当前线程已经持有写锁
            return stamp;
        }
        //有一个读锁
        else if (m == RUNIT && a != 0L) {
            //释放读锁，并尝试持有写锁
            if (U.compareAndSwapLong(this, STATE, s,
                next = s - RUNIT + WBIT))
                return next;
        }
    else
        break;
    }
    return 0L;
}

/**
 *   state匹配stamp时, 执行下列操作之一.
 1、stamp 表示持有写锁，释放写锁，并持有读锁
 2、stamp 表示持有读锁 ，返回该读锁
 3、有一个乐观读锁，只在即时可用的前提下返回一个读锁stamp
 4、其他情况都返回0，表示失败
 *
 */
public long tryConvertToReadLock(long stamp) {
    long a = stamp & ABITS, m, s, next; WNode h;
    //state匹配stamp
    while (((s = state) & SBITS) == (stamp & SBITS)) {
        //没有锁
        if ((m = s & ABITS) == 0L) {
            if (a != 0L)
            break;
        else if (m < RFULL) {
                if (U.compareAndSwapLong(this, STATE, s, next = s + RUNIT))
                    return next;
            }
            else if ((next = tryIncReaderOverflow(s)) != 0L)
            return next;
        }
        //写锁
    else if (m == WBIT) {
            //非当前线程持有写锁
            if (a != m)
                break;
            //释放写锁持有读锁
            state = next = s + (WBIT + RUNIT);
            if ((h = whead) != null && h.status != 0)
                release(h);
            return next;
        }
        //持有读锁
        else if (a != 0L && a < WBIT)
        return stamp;
    else
        break;
    }
    return 0L;
}

/**
 * If the lock state matches the given stamp then, if the stamp
 * represents holding a lock, releases it and returns an
 * observation stamp.  Or, if an optimistic read, returns it if
 * validated. This method returns zero in all other cases, and so
 * may be useful as a form of "tryUnlock".
 *
 * @param stamp a stamp
 * @return a valid optimistic read stamp, or zero on failure
 */
public long tryConvertToOptimisticRead(long stamp) {
    long a = stamp & ABITS, m, s, next; WNode h;
    U.loadFence();
    for (;;) {
        if (((s = state) & SBITS) != (stamp & SBITS))
            break;
        if ((m = s & ABITS) == 0L) {
            if (a != 0L)
            break;
            return s;
        }
    else if (m == WBIT) {
            if (a != m)
                break;
            state = next = (s += WBIT) == 0L ? ORIGIN : s;
            if ((h = whead) != null && h.status != 0)
                release(h);
            return next;
        }
        else if (a == 0L || a >= WBIT)
        break;
    else if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, next = s - RUNIT)) {
                if (m == RUNIT && (h = whead) != null && h.status != 0)
                    release(h);
                return next & SBITS;
            }
        }
        else if ((next = tryDecReaderOverflow(s)) != 0L)
        return next & SBITS;
    }
    return 0L;
}

/**
 * 如果持有写锁，释放写锁。该方法可以对发生错误后进行恢复
 * @return {@code true} if the lock was held, else false
 */
public boolean tryUnlockWrite() {
    long s; WNode h;
    //持有写锁
    if (((s = state) & WBIT) != 0L) {
        state = (s += WBIT) == 0L ? ORIGIN : s;
        if ((h = whead) != null && h.status != 0)
            release(h);
        return true;
    }
    return false;
}

/**
 * 如果持有读锁，释放一个读锁。该方法可以对发生错误后进行恢复
 *
 * @return {@code true} if the read lock was held, else false
 */
public boolean tryUnlockRead() {
    long s, m; WNode h;
    while ((m = (s = state) & ABITS) != 0L && m < WBIT) {
        if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, s - RUNIT)) {
                if (m == RUNIT && (h = whead) != null && h.status != 0)
                    release(h);
                return true;
            }
        }
        else if (tryDecReaderOverflow(s) != 0L)
        return true;
    }
    return false;
}

/**
 * 返回持有读锁的线程数
 */
private int getReadLockCount(long s) {
    long readers;
    if ((readers = s & RBITS) >= RFULL)
        readers = RFULL + readerOverflow;
    return (int) readers;
}

/**
 * 持有写锁返回true, 不持有返回false
 */
public boolean isWriteLocked() {
    return (state & WBIT) != 0L;
}

/**
 * 持有写锁返回true, 不持有返回false
 */
public boolean isReadLocked() {
    return (state & RBITS) != 0L;
}

/**
 * 返回持有读锁的线程数，用于监控系统状态
 */
public int getReadLockCount() {
    return getReadLockCount(state);
}

/**
 */
public String toString() {
    long s = state;
    return super.toString() +
        ((s & ABITS) == 0L ? "[Unlocked]" :
        (s & WBIT) != 0L ? "[Write-locked]" :
        "[Read-locks:" + getReadLockCount(s) + "]");
}

// views

/**
 * Returns a plain {@link Lock} view of this StampedLock in which
 * the {@link Lock#lock} method is mapped to {@link #readLock},
 * and similarly for other methods. The returned Lock does not
 * support a {@link Condition}; method {@link
    * Lock#newCondition()} throws {@code
 * UnsupportedOperationException}.
 *
 * @return the lock
 */
public Lock asReadLock() {
    ReadLockView v;
    return ((v = readLockView) != null ? v :
        (readLockView = new ReadLockView()));
}

/**
 * Returns a plain {@link Lock} view of this StampedLock in which
 * the {@link Lock#lock} method is mapped to {@link #writeLock},
 * and similarly for other methods. The returned Lock does not
 * support a {@link Condition}; method {@link
    * Lock#newCondition()} throws {@code
 * UnsupportedOperationException}.
 *
 * @return the lock
 */
public Lock asWriteLock() {
    WriteLockView v;
    return ((v = writeLockView) != null ? v :
        (writeLockView = new WriteLockView()));
}

/**
 * Returns a {@link ReadWriteLock} view of this StampedLock in
 * which the {@link ReadWriteLock#readLock()} method is mapped to
 * {@link #asReadLock()}, and {@link ReadWriteLock#writeLock()} to
 * {@link #asWriteLock()}.
 *
 * @return the lock
 */
public ReadWriteLock asReadWriteLock() {
    ReadWriteLockView v;
    return ((v = readWriteLockView) != null ? v :
        (readWriteLockView = new ReadWriteLockView()));
}

// view classes

final class ReadLockView implements Lock {
    public void lock() { readLock(); }
    public void lockInterruptibly() throws InterruptedException {
    readLockInterruptibly();
}
public boolean tryLock() { return tryReadLock() != 0L; }
public boolean tryLock(long time, TimeUnit unit)
throws InterruptedException {
    return tryReadLock(time, unit) != 0L;
}
public void unlock() { unstampedUnlockRead(); }
public Condition newCondition() {
    throw new UnsupportedOperationException();
}
}

final class WriteLockView implements Lock {
    public void lock() { writeLock(); }
    public void lockInterruptibly() throws InterruptedException {
    writeLockInterruptibly();
}
public boolean tryLock() { return tryWriteLock() != 0L; }
public boolean tryLock(long time, TimeUnit unit)
throws InterruptedException {
    return tryWriteLock(time, unit) != 0L;
}
public void unlock() { unstampedUnlockWrite(); }
public Condition newCondition() {
    throw new UnsupportedOperationException();
}
}

final class ReadWriteLockView implements ReadWriteLock {
    public Lock readLock() { return asReadLock(); }
    public Lock writeLock() { return asWriteLock(); }
}

// Unlock methods without stamp argument checks for view classes.
// Needed because view-class lock methods throw away stamps.

final void unstampedUnlockWrite() {
    WNode h; long s;
    if (((s = state) & WBIT) == 0L)
    throw new IllegalMonitorStateException();
    state = (s += WBIT) == 0L ? ORIGIN : s;
    if ((h = whead) != null && h.status != 0)
        release(h);
}

final void unstampedUnlockRead() {
    for (;;) {
        long s, m; WNode h;
        if ((m = (s = state) & ABITS) == 0L || m >= WBIT)
        throw new IllegalMonitorStateException();
    else if (m < RFULL) {
            if (U.compareAndSwapLong(this, STATE, s, s - RUNIT)) {
                if (m == RUNIT && (h = whead) != null && h.status != 0)
                    release(h);
                break;
            }
        }
        else if (tryDecReaderOverflow(s) != 0L)
        break;
    }
}

private void readObject(java.io.ObjectInputStream s)
throws java.io.IOException, ClassNotFoundException {
    s.defaultReadObject();
    state = ORIGIN; // reset to unlocked state
}

// internals

/**
 * 将state超过 RFULL=126的值放到readerOverflow字段中，state的前七位记录到126之后就会稳定在这个值，偶尔会到127，
 * 但是超出126的部分最终到了readerOverflow，加入获取了锁就返回stamp。
 *
 * @param s a reader overflow stamp: (s & ABITS) >= RFULL
 * @return new stamp on success, else zero
 */
private long tryIncReaderOverflow(long s) {
    // assert (s & ABITS) >= RFULL;
    if ((s & ABITS) == RFULL) {
        //将state超过 RFULL=126的值放到readerOverflow字段中，state保持不变，但锁状态+1
        if (U.compareAndSwapLong(this, STATE, s, s | RBITS)) {
            ++readerOverflow;
            state = s;
            return s;
        }
    }
    //LockSupport.nextSecondarySeed() 生成随机数
    else if ((LockSupport.nextSecondarySeed() &
        OVERFLOW_YIELD_RATE) == 0)
    //线程放弃CPU资源
        Thread.yield();
    return 0L;
}

/**
 * 尝试readerOverflow减一.
 */
private long tryDecReaderOverflow(long s) {
    // assert (s & ABITS) >= RFULL;
    if ((s & ABITS) == RFULL) {
        if (U.compareAndSwapLong(this, STATE, s, s | RBITS)) {
            int r; long next;
            if ((r = readerOverflow) > 0) {
                readerOverflow = r - 1;
                next = s;
            }
            else
                next = s - RUNIT;
            state = next;
            return next;
        }
    }
    else if ((LockSupport.nextSecondarySeed() &
        OVERFLOW_YIELD_RATE) == 0)
        Thread.yield();
    return 0L;
}

/**
 * 释放当前节点， 唤醒继任者节点线程
 */
private void release(WNode h) {
    if (h != null) {
        WNode q; Thread w;
        //将状态由WAITING改为0
        U.compareAndSwapInt(h, WSTATUS, WAITING, 0);
        //找到下个status为WAITING的节点，并唤醒线程
        if ((q = h.next) == null || q.status == CANCELLED) {
            for (WNode t = wtail; t != null && t != h; t = t.prev)
            if (t.status <= 0)
                q = t;
        }
        if (q != null && (w = q.thread) != null)
            U.unpark(w);
    }
}


private long acquireWrite(boolean interruptible, long deadline) {
    WNode node = null, p;
    for (int spins = -1;;) { // spin while enqueuing
        long m, s, ns;
        //无锁
        if ((m = (s = state) & ABITS) == 0L) {
            if (U.compareAndSwapLong(this, STATE, s, ns = s + WBIT))
                return ns;
        }
    else if (spins < 0)
        //持有写锁，并且队列为空
            spins = (m == WBIT && wtail == whead) ? SPINS : 0;
        else if (spins > 0) {
            //恒成立
            if (LockSupport.nextSecondarySeed() >= 0)
                --spins;
        }
        else if ((p = wtail) == null) {
            //初始化队列，写锁入队列
            WNode hd = new WNode(WMODE, null);
            if (U.compareAndSwapObject(this, WHEAD, null, hd))
                wtail = hd;
        }
        else if (node == null)
        //不为空，写锁入队列
            node = new WNode(WMODE, p);
        else if (node.prev != p)
            node.prev = p;
        else if (U.compareAndSwapObject(this, WTAIL, p, node)) {
            p.next = node;
            break;//入队列成功退出循环
        }
    }

    for (int spins = -1;;) {
        WNode h, np, pp; int ps;
        //前驱节点为头节点
        if ((h = whead) == p) {
            if (spins < 0)
                spins = HEAD_SPINS;
            else if (spins < MAX_HEAD_SPINS)
                spins <<= 1;
            for (int k = spins;;) { // spin at head
                long s, ns;
                //无锁
                if (((s = state) & ABITS) == 0L) {
                    if (U.compareAndSwapLong(this, STATE, s,
                        ns = s + WBIT)) {
                        //当前节点设置为头结点
                        whead = node;
                        node.prev = null;
                        return ns;
                    }
                }
            else if (LockSupport.nextSecondarySeed() >= 0 &&
                    --k <= 0)
                    break;
            }
        }
        else if (h != null) { // help release stale waiters
            WNode c; Thread w;
            //头结点为读锁将栈中所有读锁线程唤醒
            while ((c = h.cowait) != null) {
                if (U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) &&
                    (w = c.thread) != null)
                    U.unpark(w);
            }
        }
        //
        if (whead == h) {
            if ((np = node.prev) != p) {
                if (np != null)
                    (p = np).next = node;   // stale
            }
            else if ((ps = p.status) == 0)
            //前驱节点置为等待状态
                U.compareAndSwapInt(p, WSTATUS, 0, WAITING);
            else if (ps == CANCELLED) {
                if ((pp = p.prev) != null) {
                    node.prev = pp;
                    pp.next = node;
                }
            }
            else {
                long time; // 0 argument to park means no timeout
                if (deadline == 0L)
                time = 0L;
            else if ((time = deadline - System.nanoTime()) <= 0L)
                return cancelWaiter(node, node, false);
                Thread wt = Thread.currentThread();
                U.putObject(wt, PARKBLOCKER, this);
                node.thread = wt;
                if (p.status < 0 && (p != h || (state & ABITS) != 0L) &&
                whead == h && node.prev == p)
                U.park(false, time);  // emulate LockSupport.park
                node.thread = null;
                U.putObject(wt, PARKBLOCKER, null);
                if (interruptible && Thread.interrupted())
                    return cancelWaiter(node, node, true);
            }
        }
    }
}

/**
 * @param interruptible 是否允许中断
 * @param deadline 标识超时限时（0代表不限时），然后进入循环。
 * @return next state, or INTERRUPTED
 */
private long acquireRead(boolean interruptible, long deadline) {
    WNode node = null, p;
    //自旋
    for (int spins = -1;;) {
        WNode h;
        //判断队列为空
        if ((h = whead) == (p = wtail)) {
            //定义 long m,s,ns,并循环
            for (long m, s, ns;;) {
                //将state超过 RFULL=126的值放到readerOverflow字段中
                if ((m = (s = state) & ABITS) < RFULL ?
                    U.compareAndSwapLong(this, STATE, s, ns = s + RUNIT) :
                    (m < WBIT && (ns = tryIncReaderOverflow(s)) != 0L))
                //获取锁成功返回
                return ns;
                //state高8位大于0，那么说明当前锁已经被写锁独占，那么我们尝试自旋  + 随机的方式来探测状态
            else if (m >= WBIT) {
                    if (spins > 0) {
                        if (LockSupport.nextSecondarySeed() >= 0)
                            --spins;
                    }
                    else {
                        if (spins == 0) {
                            WNode nh = whead, np = wtail;
                            //一直获取锁失败，或者有线程入队列了退出内循环自旋，后续进入队列
                            if ((nh == h && np == p) || (h = nh) != (p = np))
                                break;
                        }
                        //自旋 SPINS 次
                        spins = SPINS;
                    }
                }
            }
        }
        if (p == null) {
            //初始队列
            WNode hd = new WNode(WMODE, null);
            if (U.compareAndSwapObject(this, WHEAD, null, hd))
                wtail = hd;
        }
        //当前节点为空则构建当前节点，模式为RMODE，前驱节点为p即尾节点。
        else if (node == null)
            node = new WNode(RMODE, p);
        //当前队列为空即只有一个节点（whead=wtail）或者当前尾节点的模式不是RMODE，那么我们会尝试在尾节点后面添加该节点作为尾节点
        // 然后跳出外层循环
        else if (h == p || p.mode != RMODE) {
            if (node.prev != p)
                node.prev = p;
            else if (U.compareAndSwapObject(this, WTAIL, p, node)) {
                p.next = node;
                //入队列成功，退出自旋
                break;
            }
        }
        //队列不为空并且是RMODE模式， 添加该节点到尾节点的cowait链（实际上构成一个读线程stack）中
        else if (!U.compareAndSwapObject(p, WCOWAIT,
            node.cowait = p.cowait, node))
        //失败处理
            node.cowait = null;
        else {
            //通过CAS方法将该节点node添加至尾节点的cowait链中，node成为cowait中的顶元素，cowait构成了一个LIFO队列。
            //循环
            for (;;) {
                WNode pp, c; Thread w;
                //尝试unpark头元素（whead）的cowait中的第一个元素,假如是读锁会通过循环释放cowait链
                if ((h = whead) != null && (c = h.cowait) != null &&
                    U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) &&
                    (w = c.thread) != null)

                    U.unpark(w);
                //node所在的根节点p的前驱就是whead或者p已经是whead或者p的前驱为null
                if (h == (pp = p.prev) || h == p || pp == null) {
                    long m, s, ns;
                    do {
                        //根据state再次积极的尝试获取锁
                        if ((m = (s = state) & ABITS) < RFULL ?
                            U.compareAndSwapLong(this, STATE, s,
                                ns = s + RUNIT) :
                            (m < WBIT &&
                                (ns = tryIncReaderOverflow(s)) != 0L))
                        return ns;
                    } while (m < WBIT);//条件为读模式
                }
                if (whead == h && p.prev == pp) {
                    long time;
                    if (pp == null || h == p || p.status > 0) {
                        //这样做的原因是被其他线程闯入夺取了锁，或者p已经被取消
                        node = null; // throw away
                        break;
                    }
                    if (deadline == 0L)
                    time = 0L;
                else if ((time = deadline - System.nanoTime()) <= 0L)
                    return cancelWaiter(node, p, false);
                    Thread wt = Thread.currentThread();
                    U.putObject(wt, PARKBLOCKER, this);
                    node.thread = wt;
                    if ((h != pp || (state & ABITS) == WBIT) &&
                        whead == h && p.prev == pp)
                        U.park(false, time);
                    node.thread = null;
                    U.putObject(wt, PARKBLOCKER, null);
                    //出现的中断情况下取消当前节点的cancelWaiter操作
                    if (interruptible && Thread.interrupted())
                        return cancelWaiter(node, p, true);
                }
            }
        }
    }

    for (int spins = -1;;) {
        WNode h, np, pp; int ps;
        if ((h = whead) == p) {
            if (spins < 0)
                spins = HEAD_SPINS;
            else if (spins < MAX_HEAD_SPINS)
                spins <<= 1;
            for (int k = spins;;) { // spin at head
                long m, s, ns;
                if ((m = (s = state) & ABITS) < RFULL ?
                    U.compareAndSwapLong(this, STATE, s, ns = s + RUNIT) :
                    (m < WBIT && (ns = tryIncReaderOverflow(s)) != 0L)) {
                    WNode c; Thread w;
                    whead = node;
                    node.prev = null;
                    while ((c = node.cowait) != null) {
                        if (U.compareAndSwapObject(node, WCOWAIT,
                            c, c.cowait) &&
                            (w = c.thread) != null)
                            U.unpark(w);
                    }
                    return ns;
                }
            else if (m >= WBIT &&
                    LockSupport.nextSecondarySeed() >= 0 && --k <= 0)
                    break;
            }
        }
        else if (h != null) {
            WNode c; Thread w;
            while ((c = h.cowait) != null) {
                if (U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) &&
                    (w = c.thread) != null)
                    U.unpark(w);
            }
        }
        if (whead == h) {
            if ((np = node.prev) != p) {
                if (np != null)
                    (p = np).next = node;   // stale
            }
            else if ((ps = p.status) == 0)
                U.compareAndSwapInt(p, WSTATUS, 0, WAITING);
            else if (ps == CANCELLED) {
                if ((pp = p.prev) != null) {
                    node.prev = pp;
                    pp.next = node;
                }
            }
            else {
                long time;
                if (deadline == 0L)
                time = 0L;
            else if ((time = deadline - System.nanoTime()) <= 0L)
                return cancelWaiter(node, node, false);
                Thread wt = Thread.currentThread();
                U.putObject(wt, PARKBLOCKER, this);
                node.thread = wt;
                if (p.status < 0 &&
                    (p != h || (state & ABITS) == WBIT) &&
                    whead == h && node.prev == p)
                    U.park(false, time);
                node.thread = null;
                U.putObject(wt, PARKBLOCKER, null);
                if (interruptible && Thread.interrupted())
                    return cancelWaiter(node, node, true);
            }
        }
    }
}

/**
 * 这里三个参数，node：需要删除的节点、group：可能的聚合节点、interrupted：是否被中断。
 详情如下：
 首先设置node的状态为CANCELLED，可以向其他线程传递这个节点是删除了的信息。
 然后再聚合节点gruop上清理所有状态为CANCELLED的节点（即删除节点）
 接下来假如当期node节点本身就是聚合节点，那么首先唤醒cowait链中的所有节点（读者），寻找到node后面的第一个非CANCELLED节点
 直接拼接到pred上（从而删除当前节点），然后再检查前驱节点状态，假如为CANCELLED则需也需要重置前驱节点。
 最后，在队列中不为空，并且头结点的状态为0即队列中的节点还未设置WAITING信号&当前没有持有写入锁模式&（当前没有锁或者只有乐观锁 | 
 队列中第一个等待者为读模式），那么就从队列头唤醒一次。
 */
private long cancelWaiter(WNode node, WNode group, boolean interrupted) {
    if (node != null && group != null) {
        Thread w;
        node.status = CANCELLED;
        // 移除栈中取消状态的节点
        for (WNode p = group, q; (q = p.cowait) != null;) {
            if (q.status == CANCELLED) {
                U.compareAndSwapObject(p, WCOWAIT, q, q.cowait);
                p = group; // restart
            }
            else
                p = q;
        }
        if (group == node) {
            //唤醒栈中所有非取消状态节点线程
            for (WNode r = group.cowait; r != null; r = r.cowait) {
                if ((w = r.thread) != null)
                    U.unpark(w);       // wake up uncancelled co-waiters
            }
            for (WNode pred = node.prev; pred != null; ) { // unsplice
                WNode succ, pp;        // 寻找到node后面的第一个非CANCELLED节点，直接拼接到pred上
                while ((succ = node.next) == null ||
                succ.status == CANCELLED) {
                    WNode q = null;    // 从尾节点开始查找继承者节点
                    for (WNode t = wtail; t != null && t != node; t = t.prev)
                    if (t.status != CANCELLED)
                        q = t;     // don't link if succ cancelled
                    if (succ == q ||   // ensure accurate successor
                        U.compareAndSwapObject(node, WNEXT,
                            succ, succ = q)) {
                        if (succ == null && node == wtail)
                            U.compareAndSwapObject(this, WTAIL, node, pred);
                        break;
                    }
                }
                if (pred.next == node) // 将当前节点
                    U.compareAndSwapObject(pred, WNEXT, node, succ);
                if (succ != null && (w = succ.thread) != null) {
                    succ.thread = null;
                    U.unpark(w);       // wake up succ to observe new pred
                }
                //检查前驱节点状态，假如为CANCELLED则需也需要重置前驱节点。
                if (pred.status != CANCELLED || (pp = pred.prev) == null)
                    break;
                node.prev = pp;        // repeat if new pred wrong/cancelled
                U.compareAndSwapObject(pp, WNEXT, pred, succ);
                pred = pp;
            }
        }
    }
    WNode h; // Possibly release first waiter
    while ((h = whead) != null) {
        long s; WNode q; // similar to release() but check eligibility
        if ((q = h.next) == null || q.status == CANCELLED) {
            for (WNode t = wtail; t != null && t != h; t = t.prev)
            if (t.status <= 0)
                q = t;
        }
        if (h == whead) {
            if (q != null && h.status == 0 &&
                ((s = state) & ABITS) != WBIT && // waiter is eligible
                (s == 0L || q.mode == RMODE))
            release(h);
            break;
        }
    }
    return (interrupted || Thread.interrupted()) ? INTERRUPTED : 0L;
}

// Unsafe mechanics
private static final sun.misc.Unsafe U;
private static final long STATE;
private static final long WHEAD;
private static final long WTAIL;
private static final long WNEXT;
private static final long WSTATUS;
private static final long WCOWAIT;
private static final long PARKBLOCKER;

static {
    try {
        U = sun.misc.Unsafe.getUnsafe();
        Class<?> k = StampedLock1.class;
        Class<?> wk = WNode.class;
        STATE = U.objectFieldOffset
        (k.getDeclaredField("state"));
        WHEAD = U.objectFieldOffset
        (k.getDeclaredField("whead"));
        WTAIL = U.objectFieldOffset
        (k.getDeclaredField("wtail"));
        WSTATUS = U.objectFieldOffset
        (wk.getDeclaredField("status"));
        WNEXT = U.objectFieldOffset
        (wk.getDeclaredField("next"));
        WCOWAIT = U.objectFieldOffset
        (wk.getDeclaredField("cowait"));
        Class<?> tk = Thread.class;
        PARKBLOCKER = U.objectFieldOffset
        (tk.getDeclaredField("parkBlocker"));

    } catch (Exception e) {
        throw new Error(e);
    }
}
}
```