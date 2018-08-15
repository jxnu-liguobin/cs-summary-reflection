## JDK1.9 AbstractQueuedSynchronizer[AQS]

Java中的FutureTask作为可异步执行任务并可获取执行结果而被大家所熟知。通常可以使用future.get()来获取线程的执行结果，在线程执行结束之前，get方法会一直阻塞状态，直到call()返回，其优点是使用线程异步执行任务的情况下还可以获取到线程的执行结果，但是FutureTask的以上功能却是依靠通过一个叫AbstractQueuedSynchronizer的类来实现，至少在JDK 1.5、JDK1.6版本是这样的（从1.7开始FutureTask已经被其作者Doug Lea修改为不再依赖AbstractQueuedSynchronizer实现了，这是JDK1.7的变化之一）。但是AbstractQueuedSynchronizer在JDK1.8中还有众多子类，原文是JDK1.8，部分有差异。

这些JDK中的工具类或多或少都被大家用过不止一次，比如ReentrantLock，ReentrantLock的功能是实现代码段的并发访问控制，也就是通常意义上所说的锁,
synchronized通过对monitor对象加锁来实现的。但ReentrantLock事实上它仅仅是一个工具类。[更多详情请查阅甲骨文支持网站](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html)
ReentrantLock没有使用更“高级”的机器指令，不是关键字，也不依靠JDK编译时的特殊处理，仅仅作为一个普普通通的类就完成了代码块的并发访问控制。

### AQS

它的所有子类中，要么实现并使用了它独占功能的API，要么使用了共享锁的功能，而不会同时使用两套API，即便是它最有名的子类ReentrantReadWriteLock，也是通过两个内部类：读锁和写锁，分别实现的两套API来实现的

1. 独占控制功能
2. 共享控制功能

### AQS 独占

ReentrantLock，使用过的同学应该都知道，通常是这么用它的：

```
reentrantLock.lock()
        //do something
        reentrantLock.unlock()//finally中
```
ReentrantLock就是使用AQS的独占API来实现的。
ReentrantLock会保证 do something在同一时间只有一个线程在执行这段代码，或者说，同一时刻只有一个线程的lock方法会返回。其余线程会被挂起，直到获取锁。从这里可以看出，其实ReentrantLock实现的就是一个独占锁的功能：有且只有一个线程获取到锁，其余线程全部挂起，直到该拥有锁的线程释放锁，被挂起的线程被唤醒重新开始竞争锁。
这里在看JDK9发现具体实现已变成信号量为1的类似PV的操纵

什么是信号量和PV操纵呢？这涉及到操作系统的知识：

PV操作由P操作原语和V操作原语组成（原语是不可中断的过程），对信号量进行操作，具体定义如下：

	P（S）：①将信号量S的值减1，即S=S-1；<br>
	                      ②如果S>=0，则该进程继续执行；否则该进程置为等待状态，排入等待队列。
	V（S）：①将信号量S的值加1，即S=S+1；<br>
	                      ②如果S>0，则该进程继续执行；否则释放队列中第一个等待信号量的进程。
	                      
PV操作的意义：我们用信号量及PV操作来实现进程的同步和互斥。PV操作属于进程的低级通信。
交互的并发进程因为他们共享资源，一个进程运行时，经常会由于自身或外界的原因而被中端，且断点是不固定的。也就是说进程执行的相对速度不能由进程自己来控制，于是就会导致并发进程在共享资源的时出现与时间有关的错误。

	  临界区 :    我们把并发进程中与共享变量有关的程序段称为临界区。
	  信号量S :  信号量的值与相应资源的使用情况有关。当它的值大于0时，表示当前可用资源的数量；
	  当它的值小于0时，其绝对值表示等待使用该资源的进程个数。
	  
进程的互斥是指当有若干个进程都要使用某一共享资源时，任何时刻最多只允许一个进程去使用该资源，其他要使用它的进程必须等待，直到该资源的占用着释放了该资源。
进程的同步是指在并发进程之间存在这一种制约关系，一个进程依赖另一个进程的消息，当一个进程没有得到另一个进程的消息时应等待，直到消息到达才被唤醒

ReentrantLock的定义：

```
public class ReentrantLock implements Lock, java.io.Serializable { }
```

ReentrantLock的lock方法：

```
  	public void lock() {
        sync.acquire(1);//身边只有Java9源码，sync.lock已经被acquire替换，没什么差别
        //从这里也可看出现在的ReentrantLock就是信号量为1的互斥
    }
```
如FutureTask（JDK1.6）一样，ReentrantLock内部有代理类完成具体操作，ReentrantLock只是封装了统一的一套API而已。值得注意的是，使用过ReentrantLock的同学应该知道，ReentrantLock又分为公平锁和非公平锁，所以，ReentrantLock内部只有两个sync[abstract static class Sync]的实现NonfairSync和FairSync[NonfairSync和FairSync都是static final class]。

```
 static final class NonfairSync extends Sync {}
 static final class FairSync extends Sync {}
```

* 公平锁：每个线程抢占锁的顺序为先后调用lock方法的顺序依次获取锁，类似于排队吃饭。
* 非公平锁：每个线程抢占锁的顺序不定，谁运气好，谁就获取到锁，和调用lock方法的先后顺序无关，类似于堵车时，加塞的那些xx。

到这里，通过ReentrantLock的功能和锁的所谓排不排队的方式，我们是否可以这么猜测ReentrantLock或者AQS的实现（现在不清楚谁去实现这些功能）：有那么一个被volatile修饰的标志位叫做key，用来表示有没有线程拿走了锁，或者说，锁还存不存在，还需要一个线程安全的队列，维护一堆被挂起的线程，以至于当锁被归还时，能通知到这些被挂起的线程，可以来竞争获取锁了。
公平锁和非公平锁，唯一的区别是在获取锁的时候是直接去获取锁，还是进入队列排队的问题了。

```
    public void lock() {
        sync.acquire(1);
    }
```

调用到了AQS的acquire方法，最终由AQS的acquire进行处理

```
  public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
 ```

从方法名字上看语义是，尝试获取锁，获取不到则创建一个waiter（当前线程）后放到队列中，这和我们猜测的好像很类似。

看下AQS的tryAcquire方法:

```
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

```

留空了，Doug Lea是想留给子类去实现，既然要给子类实现，应该用抽象方法，但是Doug Lea没有这么做。原因是AQS有两种功能，面向两种使用场景，需要给子类定义的方法都是抽象方法了，会导致子类无论如何都需要实现另外一种场景的抽象方法，显然，这对子类来说是不友好的。毕竟不实现所有抽象方法就只能是抽象类了

看下FairSync的tryAcquire方法：

```
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
```
getState方法是AQS的方法，因为在AQS里面有个叫state的标志位 :

```
  	protected final int getState() {
        return state;
    }
```
使用了protected修饰，表示只能是同包或子类使用[default仅是同包]。事实上，这个state就是前面我们猜想的那个“key”！

FairSync的tryAcquire方法：

```
	protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();//获取当前线程
        int c = getState();  //获取父类AQS中的标志位
        if (c == 0) {
            if (!hasQueuedPredecessors() && 
                //如果队列中没有其他线程  说明没有线程正在占有锁！
                compareAndSetState(0, acquires)) { 
                //修改一下状态位，注意：这里的acquires是在lock的时候传递来的，这个值是写死的1
                setExclusiveOwnerThread(current);
        //如果通过CAS操作将状态为更新成功则代表当前线程获取锁，因此，将当前线程设置到AQS的一个变量中，说明这个线程拿走了锁。
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
         //如果不为0 意味着，锁已经被拿走了，但是，因为ReentrantLock是重入锁，
         //是可以重复lock,unlock的，只要成对出现行。一次。这里还要再判断一次 获取锁的线程是不是当前请求锁的线程。
            int nextc = c + acquires;//如果是的，累加在state字段上就可以了。
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
```

到此，如果获取锁，tryAcquire返回true，反之，返回false，回到AQS的acquire方法。
如果没有获取到锁，按照我们的描述，应该将当前线程放到队列中去，只不过，在放之前，需要做些包装。

AQS的addWaiter方法：

```
    private Node addWaiter(Node mode) {
        Node node = new Node(mode);
        for (;;) {
            Node oldTail = tail;
            if (oldTail != null) {
                node.setPrevRelaxed(oldTail);
                if (compareAndSetTail(oldTail, node)) {
                    oldTail.next = node;
                    return node;
                }
            } else {
                initializeSyncQueue();
            }
        }
    }
```
Node类型定义在
用当前线程去构造一个Node对象，node是一个表示Node类型的字段，仅仅表示这个节点是独占的，还是共享的，或者说，AQS的这个队列中，哪些节点是独占的，哪些是共享的。
这里lock调用的是AQS独占的API，当然，可以写死是独占状态的节点。
创建好节点后，将节点加入到队列尾部，此处，在队列不为空的时候，先尝试通过cas方式修改尾节点为最新的节点，如果修改失败，意味着有并发，这个时候才会进入AQS的initializeSyncQueue方法中,下面是initializeSyncQueue方法：

```
    private final void initializeSyncQueue() {
        Node h;
        if (HEAD.compareAndSet(this, null, (h = new Node())))
            tail = h;
    }
```

在第一次争用时初始化头和尾字段

将线程的节点接入到队里中后，当然还需要做一件事:将当前线程挂起！这个事，由acquireQueued来做。
我们知道，队列由Node类型的节点组成，其中至少有两个变量，一个封装线程，一个封装节点类型。
而实际上，它的内存结构是这样的（第一次节点插入时，第一个节点是一个空节点，代表有一个线程已经获取锁，事实上，队列的第一个节点就是代表持有锁的节点）
每次有线程竞争失败，进入队列后其实都是插入到队列的尾节点（tail后面）后面

再回来看看AQS的acquireQueued方法：

```
    final boolean acquireQueued(final Node node, int arg) {
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
              //如果当前的节点是head说明他是队列中第一个“有效的”节点，因此尝试获取，上文中有提到这个类是交给子类去扩展的。
                  //成功后，将上图中的黄色节点移除，Node1变成头节点。
                    setHead(node);
                    p.next = null; // help GC
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())//否则，检查前一个节点的状态为，看当前获取锁失败的线程是否需要挂起。
                    interrupted = true;//如果需要，借助JUC包下的LockSopport类的静态方法Park挂起当前线程。直到被唤醒。
            }
        } catch (Throwable t) {
            cancelAcquire(node);//如果有异常
            throw t;// 取消请求，对应到队列操作，就是将当前节点从队列中移除。
        }
    }
```

这块代码有几点需要说明：

Node节点中，除了存储当前线程，节点类型，队列中前后元素的变量，还有一个叫waitStatus的变量，该变量用于描述节点的状态，为什么需要这个状态呢？

原因是：AQS的队列中，在有并发时，肯定会存取一定数量的节点，每个节点[代表了一个线程的状态，有的线程可能“等不及”获取锁了，需要放弃竞争，退出队列，有的线程在等待一些条件满足，满足后才恢复执行（这里的描述很像某个J.U.C包下的工具类，ReentrankLock的Condition，事实上，Condition同样也是AQS的子类）等等，总之，各个线程有各个线程的状态，但总需要一个变量来描述它，这个变量就叫waitStatus，在AQS的Node[static final class Node]中定义了它的四种状态：

```
     /** waitStatus value to indicate thread has cancelled. */
        static final int CANCELLED =  1;
        /** waitStatus value to indicate successor's thread needs unparking. */
        static final int SIGNAL    = -1;
        /** waitStatus value to indicate thread is waiting on condition. */
        static final int CONDITION = -2;
        /**
         * waitStatus value to indicate the next acquireShared should
         * unconditionally propagate.
         */
        static final int PROPAGATE = -3;
```

	分别表示：
	
	1. 节点取消
	2. 节点等待触发
	3. 节点等待条件
	4. 节点状态需要向后传播。
	只有当前节点的前一个节点为SIGNAL时，才能当前节点才能被挂起。

对线程的挂起及唤醒操作是通过使用UNSAFE类调用JNI方法实现的。当然，还提供了挂起指定时间后唤醒的API。
到此为止，一个线程对于锁的一次竞争才告于段落，结果有两种，要么成功获取到锁（不用进入到AQS队列中），要么，获取失败，被挂起，等待下次唤醒后继续循环尝试获取锁，值得注意的是，AQS的队列为FIFO队列，所以，每次被CPU假唤醒，且当前线程不是处在头节点的位置，也是会被挂起的。AQS通过这样的方式，实现了竞争的排队策略。

看完了获取锁，在看看释放锁，具体看代码之前，我们可以先继续猜下，释放操作需要做哪些事情：
	
	1. 因为获取锁的线程的节点，此时在AQS的头节点位置，所以，可能需要将头节点移除。
	2. 而应该是直接释放锁，然后找到AQS的头节点，通知它可以来竞争锁了。

是不是这样呢?我们继续来看下，同样我们用ReentrantLock的FairSync来说明：
(FairSync继承自AQS，直接调用的父类的release方法，而NonfairSync和FairSync则继承了Sync，不同的就是公平锁重写了tryAcquire方法)

```
  public void unlock() {
        sync.release(1);
    }
```
查看AQS的release

```
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
```
unlock方法调用了AQS的release方法，同样传入了参数1，和获取锁的相应对应

	获取一个锁：标示为+1
	释放一个锁：标志位-1
	
ReentranLock的tryRelease方法

```
	protected final boolean tryRelease(int releases) {
        int c = getState() - releases; 
        if (Thread.currentThread() != getExclusiveOwnerThread()) 
        //如果释放的线程和获取锁的线程不是同一个，抛出非法监视器状态异常。
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
        //因为是重入的关系，不是每次释放锁c都等于0，直到最后一次释放锁时，才通知AQS不需要再记录哪个线程正在获取锁。
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }
```

释放锁，成功后，找到AQS的头节点，并唤醒它即可：

AQS的unparkSuccessor方法负责唤醒：


```
    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;
        if (ws < 0)
            node.compareAndSetWaitStatus(ws, 0);

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node p = tail; p != node && p != null; p = p.prev)
                if (p.waitStatus <= 0)
                    s = p;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }
```
值得注意的是，寻找的顺序是从队列尾部开始往前去找的最前面的一个waitStatus小于0的节点。

到此，ReentrantLock的lock和unlock方法已经基本解析完毕了，唯独还剩下一个非公平锁NonfairSync没说，其实，它和公平锁的唯一区别就是获取锁的方式不同，一个是按前后顺序一次获取锁，一个是抢占式的获取锁，那ReentrantLock是怎么实现的呢？再看两段代码：

```
  	final boolean nonfairTryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
        
```
```
   static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }
```
可见NonfairSync非公平锁的处理方式是: 使用父类Sync的nonfairTryAcquire方法，在lock的时候先直接CAS修改一次state变量（尝试获取锁），成功就返回，不成功再排队，从而达到不排队直接抢占的目的。

而对于公平锁是这样的：

```
	protected final boolean tryAcquire(int acquires) {
	    final Thread current = Thread.currentThread();
	    int c = getState();
	    if (c == 0) {
	        if (!hasQueuedPredecessors() &&
	            compareAndSetState(0, acquires)) {
	            setExclusiveOwnerThread(current);
	            return true;
	        }
	    }
	    else if (current == getExclusiveOwnerThread()) {
	        int nextc = c + acquires;
	        if (nextc < 0)
	            throw new Error("Maximum lock count exceeded");
	        setState(nextc);
	        return true;
	    }
	    return false;
	}
```
显然，它会老老实实的开始就走AQS的流程排队获取锁。如果前面有人调用过其lock方法，则排在队列中前面，也就更有机会更早的获取锁，从而达到“公平”的目的。

总结：
站在AQS的层面state可以表示锁，也可以表示其他状态，它并不关心它的子类把它变成一个什么工具类，而只是提供了一套维护一个独占状态。甚至，最准确的是AQS只是维护了一个状态，因为，别忘了，它还有一套共享状态的API，所以，AQS只是维护一个状态，一个控制各个线程何时可以访问的状态，它只对状态负责，而这个状态表示什么含义，由子类自己去定义。

### AQS共享

CountDownLatch为java.util.concurrent包下的计数器工具类，常被用在多线程环境下，它在初始时需要指定一个计数器的大小，然后可被多个线程并发的实现减1操作，并在计数器为0后调用await方法的线程被唤醒，从而实现多线程间的协作。它在多线程环境下的基本使用方式为：

```
	  //main thread
	  // 新建一个CountDownLatch，并指制定一个初始大小
	  CountDownLatch countDownLatch = new CountDownLatch(3);
	  // 调用await方法后，main线程将阻塞在这里，直到countDownLatch 中的计数为0 
	  countDownLatch.await();
	  System.out.println("over");
	
	 //thread1
	 // do something 
	 //...........
	 //调用countDown方法，将计数减1
	  countDownLatch.countDown();
	
	
	 //thread2
	 // do something 
	 //...........
	 //调用countDown方法，将计数减1
	  countDownLatch.countDown();
	
	   //thread3
	 // do something 
	 //...........
	 //调用countDown方法，将计数减1
	  countDownLatch.countDown();
```
注意，线程thread 1,2,3各自调用 countDown后，countDownLatch 的计数为0，await方法返回，控制台输入“over”,在此之前main thread 会一直沉睡。
可以看到CountDownLatch的作用类似于一个“栏栅”，在CountDownLatch的计数为0前，调用await方法的线程将一直阻塞，直到CountDownLatch计数为0，await方法才会返回，而CountDownLatch的countDown()方法则一般由各个线程调用，实现CountDownLatch计数的减1。此时CountDownLatch的计数是由各个线程之间共享的。

从new CountDownLatch（3）开始，看看CountDownLatch是怎么实现的。

CountDownLatch的构造方法：

```
  public CountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }
```
CountDownLatch的定义：

```
public class CountDownLatch { }
```
和ReentrantLock类似，CountDownLatch内部也有一个叫做Sync的内部类，同样也是用它继承了AQS。

再看下Sync类：

```
    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c - 1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }
```
上面的参数count就是State，也就是上面猜测的AQS的状态位，在不同的场景下，代表不同的含义，比如在ReentrantLock中，表示加锁的次数，在CountDownLatch中，则表示CountDownLatch的计数器的初始大小。

设置完计数器大小后CountDownLatch的构造方法返回，下面我们再看下CountDownLatch的await()方法：

```
  public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }
```
调用了Sync的acquireSharedInterruptibly方法，因为Sync是AQS子类的原因，这里其实是直接调用了AQS的acquireSharedInterruptibly方法：

```
    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }
```
从方法名上看，这个方法的调用是响应线程的打断的，所以在前两行会检查下线程是否被打断。接着，尝试着获取共享锁，小于0，表示获取失败

我们知道AQS在获取锁的思路是，先尝试直接获取锁，如果失败会将当前线程放在队列中，按照FIFO的原则等待锁。而对于共享锁也是这个思路，和独占锁一致，这里的tryAcquireShared也是个空方法，留给子类去判断，AQS的tryAcquireShared：

```
  protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
```
而在CountDownLatch的内部类Sync中实现了tryAcquireShared方法：

```
 protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }
```
如果state变成0了，则返回1，表示获取成功，否则返回-1则表示获取失败。
看到这里，读者可能会发现， await方法的获取方式更像是在获取一个独占锁，那为什么这里还会用tryAcquireShared呢？
回想下CountDownLatch的await方法是不是只能在主线程中调用？答案是否定的，CountDownLatch的await方法可以在多个线程中调用，当CountDownLatch的计数器为0后，调用await的方法都会依次返回。 也就是说可以多个线程同时在等待await方法返回，所以它被设计成了实现tryAcquireShared方法，获取的是一个共享锁，锁在所有调用await方法的线程间共享，所以叫共享锁。

回到acquireSharedInterruptibly方法，如果获取共享锁失败（返回了-1，说明state不为0，也就是CountDownLatch的计数器还不为0），进入调用doAcquireSharedInterruptibly方法中，将当前线程放入到队列中去。

回顾AQS队列的数据结构：AQS是一个双向链表，通过节点中的next，pre变量分别指向当前节点后一个节点和前一个节点。其中，每个节点中都包含了一个线程和一个类型变量：表示当前节点是独占节点还是共享节点，头节点中的线程为正在占有锁的线程，而后的所有节点的线程表示为正在等待获取锁的线程。

AQS的doAcquireSharedInterruptibly方法：

```
	private void doAcquireSharedInterruptibly(int arg)
		throws InterruptedException {
        final Node node = addWaiter(Node.SHARED);
        //将当前线程包装为类型为Node.SHARED的节点，标示这是一个共享节点。
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
         //如果新建节点的前一个节点，就是Head，说明当前节点是AQS队列中等待获取锁的第一个节点，
		//按照FIFO的原则，可以直接尝试获取锁。
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
           //获取成功，需要将当前节点设置为AQS队列中的第一个节点，这是AQS的规则
           //队列的头节点表示正在获取锁的节点
                        p.next = null; // help GC
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())//检查下是否需要将当前节点挂起
                    throw new InterruptedException();
            }
        } catch (Throwable t) {
            cancelAcquire(node);
            throw t;
        }
    }
```
这里有几点需要说明的：
1. setHeadAndPropagate方法：

```
   private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; // Record old head for check below
        setHead(node);
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
            (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
```
首先，使用了CAS更换了头节点，然后，将当前节点的下一个节点取出来，如果同样是“shared”类型的，再做一个"releaseShared"操作。
看下doReleaseShared方法：

```
    private void doReleaseShared() {
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {
                    if (!h.compareAndSetWaitStatus(Node.SIGNAL, 0))
                    /如果当前节点是SIGNAL意味着，它正在等待一个信号，  
                    //或者说，它在等待被唤醒，因此做两件事，1是重置waitStatus标志位
                    //2是重置成功后,唤醒下一个节点。
                        continue;            // loop to recheck cases
                    unparkSuccessor(h);
                }
                else if (ws == 0 &&
                         !h.compareAndSetWaitStatus(0, Node.PROPAGATE))
                         //如果本身头节点的waitStatus是出于重置状态（waitStatus==0）的，将其设置为“传播”状态。
						//意味着需要将状态向后一个节点传播。
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }
```
为什么要这么做呢？这就是共享功能和独占功能最不一样的地方，对于独占功能来说，有且只有一个线程（通常只对应一个节点，拿ReentantLock举例，如果当前持有锁的线程重复调用lock()方法，那根据本系列上半部分我们的介绍，我们知道，会被包装成多个节点在AQS的队列中，所以用一个线程来描述更准确），能够获取锁，但是对于共享功能来说。
共享的状态是可以被共享的，通过AQS的unparkSuccessor()方法唤醒。所以其他AQS队列中的其他节点也应能第一时间知道状态的变化。

2.对于doAcquireShared方法，AQS还提供了集中类似的实现：
分别对应了：

	带参数请求共享锁。 （忽略中断）
	带参数请求共享锁，且响应中断。（每次循环时，会检查当前线程的中断状态，以实现对线程中断的响应）
	带参数请求共享锁但是限制等待时间。（第二个参数设置超时时间，超出时间后，方法返回。）
比较特别的为最后一个doAcquireSharedNanos方法，我们一起看下它怎么实现超时时间的控制的。
因为该方法和其余获取共享锁的方法逻辑是类似的，也就是实现超时时间控制的地方不同。

可以看到，其实就是在进入方法时，计算出了一个“deadline”，每次循环的时候用当前时间和“deadline”比较，大于“dealine”说明超时时间已到，直接返回方法。

注意这行代码：

```
nanosTimeout > SPIN_FOR_TIMEOUT_THRESHOLD
```
其中SPIN_FOR_TIMEOUT_THRESHOLD是AQS中的一个常量：

```
static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000L;
```
从变量的字面意思可知，这是拿超时时间和超时自旋的最小作比较，在这里Doug Lea把超时自旋的阈值设置成了1000ns,即只有超时时间大于1000ns才会去挂起线程，否则，再次循环，以实现“自旋”操作。这是“自旋”在AQS中的应用之处。

看完await方法，再来看下countDown()方法：

```
  public void countDown() {
        sync.releaseShared(1);
    }
```
调用了AQS的releaseShared方法,并传入了参数1:

```
    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }
```
同样先尝试去释放锁，tryReleaseShared同样为空方法，留给子类自己去实现，以下是CountDownLatch的内部类Sync的实现：

```
     protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c - 1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
```
死循环更新state的值，实现state的减1操作，之所以用死循环是为了确保state值的更新成功。
从上文的分析中可知，如果state的值为0，在CountDownLatch中意味：所有的子线程已经执行完毕，这个时候可以唤醒调用await()方法的线程了，而这些线程正在AQS的队列中，并被挂起的，
所以下一步应该去唤醒AQS队列中的头节点了（AQS的队列为FIFO队列），然后由头节点去依次唤醒AQS队列中的其他共享节点。
如果tryReleaseShared返回true,进入AQS的doReleaseShared()方法：

```
    private void doReleaseShared() {
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {
                    if (!h.compareAndSetWaitStatus(Node.SIGNAL, 0))
               //如果当前节点是SIGNAL意味着，它正在等待一个信号，
			//或者说，它在等待被唤醒，因此做两件事，1是重置waitStatus标志位，2是重置成功后,唤醒下一个节点。
                        continue;            // loop to recheck cases
                    unparkSuccessor(h);
                }
                else if (ws == 0 &&
                         !h.compareAndSetWaitStatus(0, Node.PROPAGATE))
                   //如果本身头节点的waitStatus是出于重置状态（waitStatus==0）的，将其设置为“传播”状态。
					//意味着需要将状态向后一个节点传播。
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }
```
当线程被唤醒后，会重新尝试获取共享锁，而对于CountDownLatch线程获取共享锁判断依据是state是否为0，而这个时候显然state已经变成了0，因此可以顺利获取共享锁并且依次唤醒AQS队里中后面的节点及对应的线程。

AQS关于共享锁方面的实现方式：

如果获取共享锁失败后，将请求共享锁的线程封装成Node对象放入AQS的队列中，并挂起Node对象对应的线程，实现请求锁线程的等待操作。待共享锁可以被获取后，从头节点开始，依次唤醒头节点及其以后的所有共享类型的节点。实现共享状态的传播。

这里有几点值得注意：

1. 与AQS的独占功能一样，共享锁是否可以被获取的判断为空方法，交由子类去实现。
2. 与AQS的独占功能不同，当锁被头节点获取后，独占功能是只有头节点获取锁，其余节点的线程继续沉睡，等待锁被释放后，才会唤醒下一个节点的线程，而共享功能是只要头节点获取锁成功，就在唤醒自身节点对应的线程的同时，继续唤醒AQS队列中的下一个节点的线程，每个节点在唤醒自身的同时还会唤醒下一个节点对应的线程，以实现共享状态的“向后传播”，从而实现共享功能。

总结：

首先，AQS并不关心“是什么锁”，对于AQS来说它只是实现了一系列的用于判断“资源”是否可以访问的API,并且封装了在“访问资源”受限时将请求访问的线程的加入队列、挂起、唤醒等操作， AQS只关心“资源不可以访问时，怎么处理？”、“资源是可以被同时访问，还是在同一时间只能被一个线程访问？”、“如果有线程等不及资源了，怎么从AQS的队列中退出？”等一系列围绕资源访问的问题，而至于“资源是否可以被访问？”这个问题则交给AQS的子类去实现。

当AQS的子类是实现独占功能时，例如ReentrantLock，“资源是否可以被访问”被定义为只要AQS的state变量不为0，并且持有锁的线程不是当前线程，则代表资源不能访问。
当AQS的子类是实现共享功能时，例如：CountDownLatch，“资源是否可以被访问”被定义为只要AQS的state变量不为0，说明资源不能访问。
这是典型的将规则和操作分开的设计思路：规则子类定义，操作逻辑因为具有公用性，放在父类中去封装。

当然，正式因为AQS只是关心“资源在什么条件下可被访问”，所以子类还可以同时使用AQS的共享功能和独占功能的API以实现更为复杂的功能。

比如：ReentrantReadWriteLock，我们知道ReentrantReadWriteLock的中也有一个叫Sync的内部类继承了AQS，而AQS的队列可以同时存放共享锁和独占锁，对于ReentrantReadWriteLock来说分别代表读锁和写锁，当队列中的头节点为读锁时，代表读操作可以执行，而写操作不能执行，因此请求写操作的线程会被挂起，当读操作依次推出后，写锁成为头节点，请求写操作的线程被唤醒，可以执行写操作，而此时的读请求将被封装成Node放入AQS的队列中。如此往复，实现读写锁的读写交替进行。

文章上半部分提到的FutureTask，其实思路也是：封装一个存放线程执行结果的变量A,使用AQS的独占API实现线程对变量A的独占访问，判断规则是，线程没有执行完毕：call()方法没有返回前，不能访问变量A，或者是超时时间没到前不能访问变量A(这就是FutureTask的get方法可以实现获取线程执行结果时，设置超时时间的原因)。


修改|补充|转载| [infoq](http://www.infoq.com/cn/articles/java8-abstractqueuedsynchronizer)
