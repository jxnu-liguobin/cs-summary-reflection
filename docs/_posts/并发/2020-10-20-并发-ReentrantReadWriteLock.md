---
title: ReentrantReadWriteLock
categories:
- 并发
tags: [Java并发]
---

* 目录
{:toc}

# 介绍

AQS（`AbstractQueuedSynchronizer`）支持独占式同步状态获取/释放、共享式同步状态获取/释放两种模式，对应的典型应用分别是`ReentrantLock`和`Semaphore`，AQS还可以混合两种模式使用，读写锁`ReentrantReadWriteLock`就是如此。

`ReentrantReadWriteLock`有如下特性：
- 获取顺序
    - 非公平模式（默认） 当以非公平初始化时，读锁和写锁的获取的顺序是不确定的。非公平锁主张竞争获取，可能会延缓一个或多个读或写线程，但是会比公平锁有更高的吞吐量。
    - 公平模式 当以公平模式初始化时，线程将会以队列的顺序获取锁。当当前线程释放锁后，等待时间最长的写锁线程就会被分配写锁；或者有一组读线程组等待时间比写线程长，那么这组读线程组将会被分配读锁。有写线程持有写锁或者有等待的写线程时，一个尝试获取公平的读锁（非重入）的线程就会阻塞。这个线程直到等待时间最长的写锁获得锁后并释放掉锁后才能获取到读锁。
- 可重入 允许读锁可写锁可重入。写锁可以获得读锁，读锁不能获得写锁。
- 锁降级 允许写锁降低为读锁
- 中断锁的获取 在读锁和写锁的获取过程中支持中断
- 支持Condition 写锁提供Condition实现
- 监控 提供确定锁是否被持有等辅助方法

情景：我们在系统中有一个多线程访问的缓存，多个线程都可以对缓存进行读或写操作，但是读操作远远多于写操作，要求写操作要线程安全，且写操作执行完成要求对当前的所有读操作马上可见。

# 示例

分析上面的需求：因为有多个线程可能会执行写操作，因此多个线程的写操作必须同步串行执行；而写操作执行完成要求对当前的所有读操作马上可见，这就意味着当有线程正在读的时候，要阻塞写操作，当正在执行写操作时，要阻塞读操作。一个简单的实现就是将数据直接加上互斥锁，同一时刻不管是读还是写线程，都只能有一个线程操作数据。但是这样的问题就是如果当前只有N个读线程，没有写线程，这N个读线程也要排队读，尽管其实是可以安全并发提高效率的。因此理想的实现是：
- 当有写线程时，则写线程独占同步状态。
- 当没有写线程时只有读线程时，则多个读线程可以共享同步状态。

根据上面需求，我们可以设计出一个线程安全的简易缓存（为了方便，我们直接使用HashMap存储）：
```java
public class ReadWriteCache {
    private static Map<String, Object> data = new HashMap<>();
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    public static Object get(String key) {
        rlock.lock();
        try {
            return data.get(key);
        } finally {
            rlock.unlock();
        }
    }

    public static Object put(String key, Object value) {
        wlock.lock();
        try {
            return data.put(key, value);
        } finally {
            wlock.unlock();
        }
    }
}
```

同样为了简便，我们只为缓存实现了两个方法get和put。

从上面代码可以看出，我们先创建一个`ReentrantReadWriteLock`对象，构造函数false代表是非公平的（非公平的含义和`ReentrantLock`相同）。然后通过`readLock`、`writeLock`方法分别获取读锁和写锁。在做读操作的时候（get方法），我们要先获取读锁；在做写操作的时候（put方法），我们要先获取写锁。

# 源码

下面是ReentrantReadWriteLock的源码（仅包含类结构，不含具体实现代码）。
```java
public class ReentrantReadWriteLock implements ReadWriteLock, java.io.Serializable {
    private final ReentrantReadWriteLock.ReadLock readerLock;
    private final ReentrantReadWriteLock.WriteLock writerLock;
    final Sync sync;

    public ReentrantReadWriteLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
    }

    public ReentrantReadWriteLock.WriteLock writeLock() { return writerLock; }
    public ReentrantReadWriteLock.ReadLock  readLock()  { return readerLock; }

    abstract static class Sync extends AbstractQueuedSynchronizer {}
    static final class NonfairSync extends Sync {}
    static final class FairSync extends Sync {}
    public static class ReadLock implements Lock, java.io.Serializable {}
    public static class WriteLock implements Lock, java.io.Serializable {}
}
```

可以看到，在公平锁与非公平锁的实现上，与`ReentrantLock`一样，也是有一个继承AQS的内部类`Sync`，然后`NonfairSync`和`FairSync`都继承`Sync`，通过构造函数传入的布尔值决定要构造哪一种`Sync`实例。

读写锁比`ReentrantLock`多出了两个内部类：`ReadLock`和`WriteLock`， 用来定义读锁和写锁，然后在构造函数中，会构造一个读锁和一个写锁实例保存到成员变量`readerLock`和`writerLock`。我们在上面的示例中使用到的`readLock()`和`writeLock()`方法就是返回这两个成员变量保存的锁实例。

我们在Sync类中可以看到下列代码：

```java
static final int SHARED_SHIFT   = 16;
static final int SHARED_UNIT    = (1 << SHARED_SHIFT);      //每次要让共享锁+1，就应该让state加 1<<16
static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;  //每种锁的最大重入数量
static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

/** Returns the number of shared holds represented in count  */
static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
/** Returns the number of exclusive holds represented in count  */
static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }
```

可以看到主要是几个移位操作，通过上面的整体结构，我们知道了在读写锁内保存了读锁和写锁的两个实例。之前在`ReentrantLock`中，我们知道锁的状态是保存在`Sync`实例的`state`字段中的（继承自父类AQS），现在有了读写两把锁，然而可以看到还是只有一个`Sync`实例，那么一个`Sync`实例的`state`是如何同时保存两把锁的状态的呢？答案就是用了位分隔：

- state字段是32位的int，读写锁用state的低16位保存写锁（独占锁）的状态；高16位保存读锁（共享锁）的状态。
- 因此要获取独占锁当前的重入数量，就是 `state & ((1 << 16) -1)`，即`exclusiveCount`方法，移位减一后就变成全1了，按位与操作就是只有对应的两个二进位都为1时，结果位才为1，所以其结果取决于`state`的低16位（此时16之前是补的0，所以与高位无关）
- 要获取共享锁当前的重入数量，就是 state >>> 16（即 sharedCount 方法）

下面我们具体看写锁和读锁的实现。

## 写锁

看下`WriteLock`类中的`lock`和`unlock`方法：
```java
public void lock() {
    sync.acquire(1);
}

public void unlock() {
    sync.release(1);
}
```

可以看到就是调用的独占式同步状态的获取与释放，因此真实的实现就是`Sync`的`tryAcquire`和`tryRelease`。

### 写锁的获取

看下`tryAcquire`：
```java
protected final boolean tryAcquire(int acquires) {
    Thread current = Thread.currentThread();
    int c = getState();
    int w = exclusiveCount(c); //获取独占锁的重入数
    if (c != 0) {
        // 当前state不为0，此时：如果写锁状态为0说明读锁此时被占用返回false；如果写锁状态不为0且写锁没有被当前线程持有返回false
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded"); //写锁重入数溢出
        // 写锁重入，返回true
        setState(c + acquires);
        return true;
    }
    //到这里了说明state为0，尝试直接cas。writerShouldBlock是为了实现公平或非公平策略的
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}
```

### 写锁的释放

看下`tryRelease`：
```java
protected final boolean tryRelease(int releases) {
    if (!isHeldExclusively())
        //非独占模式直接抛异常
        throw new IllegalMonitorStateException(); 
    int nextc = getState() - releases;
    boolean free = exclusiveCount(nextc) == 0;
    if (free) 
        setExclusiveOwnerThread(null); //如果独占模式重入数为0了，说明独占模式被释放
    setState(nextc);  //不管独占模式是否被释放，更新独占重入数
    return free;
}
```

## 读锁

类似于写锁，读锁的`lock`和`unlock`的实际实现对应`Sync`的`tryAcquireShared`和`tryReleaseShared`方法。

### 读锁的获取

看下`tryAcquireShared`：
```java
protected final int tryAcquireShared(int unused) {
    Thread current = Thread.currentThread();
    int c = getState();
    if (exclusiveCount(c) != 0 &&
        getExclusiveOwnerThread() != current)
        //如果独占模式被占且不是当前线程持有，则获取失败
        return -1; 
    int r = sharedCount(c);            
    //如果公平策略没有要求阻塞且重入数没有到达最大值，则直接尝试CAS更新state
    if (!readerShouldBlock() &&
        r < MAX_COUNT &&
        compareAndSetState(c, c + SHARED_UNIT)) {                
        //更新成功后会在firstReaderHoldCount中或readHolds(ThreadLocal类型的)的本线程副本中记录当前线程重入数（下面的if else if else 代码块），这是为了实现jdk1.6中加入的getReadHoldCount()方法的，这个方法能获取当前线程重入共享锁的次数(state中记录的是多个线程的总重入次数)，加入了这个方法让代码复杂了不少，但是其原理还是很简单的：如果当前只有一个线程的话，还不需要动用ThreadLocal，直接往firstReaderHoldCount这个成员变量里存重入数，当有第二个线程来的时候，就要动用ThreadLocal变量readHolds了，每个线程拥有自己的副本，用来保存自己的重入数。
        if (r == 0) {
            //第一个读线程就是当前线程
            firstReader = current;
            firstReaderHoldCount = 1;
        } else if (firstReader == current) {
            //如果当前线程重入了，记录firstReaderHoldCount
            firstReaderHoldCount++;
        } else {
            //当前读线程和第一个读线程不同，记录每一个线程读的次数
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                cachedHoldCounter = rh = readHolds.get();
            else if (rh.count == 0)
                readHolds.set(rh);
            rh.count++;
        }
        return 1;
    }
    //用来处理CAS没成功的情况，逻辑和上面的逻辑是类似的，就是加了无限循环
    return fullTryAcquireShared(current); 
}
```

下面这个方法就不用细说了，和上面的处理逻辑类似，加了无限循环用来处理CAS失败的情况。
```java
final int fullTryAcquireShared(Thread current) {
    HoldCounter rh = null;
    for (;;) {
        int c = getState();
        if (exclusiveCount(c) != 0) {
            if (getExclusiveOwnerThread() != current)
                return -1;
            // else we hold the exclusive lock; blocking here
            // would cause deadlock.
        } else if (readerShouldBlock()) {
            // Make sure we're not acquiring read lock reentrantly
            if (firstReader == current) {
                // assert firstReaderHoldCount > 0;
            } else {
                if (rh == null) {
                    rh = cachedHoldCounter;
                    if (rh == null || rh.tid != getThreadId(current)) {
                        rh = readHolds.get();
                        if (rh.count == 0)
                            readHolds.remove();
                    }
                }
                if (rh.count == 0)
                    return -1;
            }
        }
        if (sharedCount(c) == MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        if (compareAndSetState(c, c + SHARED_UNIT)) {
            if (sharedCount(c) == 0) {
                firstReader = current;
                firstReaderHoldCount = 1;
            } else if (firstReader == current) {
                firstReaderHoldCount++;
            } else {
                if (rh == null)
                    rh = cachedHoldCounter;
                if (rh == null || rh.tid != getThreadId(current))
                    rh = readHolds.get();
                else if (rh.count == 0)
                    readHolds.set(rh);
                rh.count++;
                cachedHoldCounter = rh; // cache for release
            }
            return 1;
        }
    }
}
```

### 读锁的释放

看下`tryReleaseShared`：
```java
protected final boolean tryReleaseShared(int unused) {
    //得到调用unlock的线程
    Thread current = Thread.currentThread(); 
    //下面第一个if else if else 代码块也是为了实现jdk1.6中加入的getReadHoldCount()方法，在更新当前线程的重入数。
    if (firstReader == current) {
        // assert firstReaderHoldCount > 0;
        if (firstReaderHoldCount == 1)
            firstReader = null;
        else
            firstReaderHoldCount--;
    } else {
        HoldCounter rh = cachedHoldCounter;
        if (rh == null || rh.tid != getThreadId(current))
            rh = readHolds.get();
        int count = rh.count;
        if (count <= 1) {
            readHolds.remove();
            if (count <= 0)
                throw unmatchedUnlockException();
        }
        --rh.count;
    } 
    //这里是真正的释放同步状态的逻辑，就是直接同步状态-SHARED_UNIT，然后CAS更新。
    for (;;) {
        int c = getState();
        int nextc = c - SHARED_UNIT;
        if (compareAndSetState(c, nextc))
            // Releasing the read lock has no effect on readers,
            // but it may allow waiting writers to proceed if
            // both read and write locks are now free.
            return nextc == 0;
    }
}
```

通过上面的源码分析，我们可以发现一个现象：

* 在线程持有读锁的情况下，该线程不能取得写锁（因为获取写锁的时候，如果发现当前的读锁被占用，就马上获取失败，不管读锁是不是被当前线程持有）
* 在线程持有写锁的情况下，该线程可以继续获取读锁（获取读锁时如果发现写锁被占用，只有写锁没有被当前线程占用的情况才会获取失败）
    - 因为当线程获取读锁的时候，可能有其他线程同时也在持有读锁，因此不能把获取读锁的线程“升级”为写锁；
    - 而对于获得写锁的线程，它一定独占了读写锁，因此可以继续让它获取读锁，当它同时获取了写锁和读锁后，还可以先释放写锁继续持有读锁，这样一个写锁就“降级”为了读锁。
* 一个线程要想同时持有写锁和读锁，必须先获取写锁再获取读锁；
* 写锁可以“降级”为读锁；读锁不能“升级”为写锁。