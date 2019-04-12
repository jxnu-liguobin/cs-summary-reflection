## StampedLock实现原理 

### StampedLock的实现

1. 基于CLH锁->一种自旋锁，保证没有饥饿发生，FIFO顺序【先进先出】
2. 维护一个线程队列，申请不成功的记录在此，每个节点保存一个标记位【locked】，用来判断当前线程是否释放锁
3. 当线程试图获取锁，取得当前队列的尾部节点作为其前序节点，并使用类似while(pred.locked){}判断前序节点是否已经成功释放锁
4. 只要前序节点没有释放锁，则表示当前线程还不能继续执行，自旋等待。
5. 如果前序线程已经释放，则当前线程可以继续执行
6. 释放锁时线程将自己的节点标记位置为false，那么后续等待的线程就能继续执行了

StampedLock使用起来更复杂。它们使用了一个票据（stamp）的概念，这是一个long值，在加锁和解锁操作时，它被用作一张门票。
这意味着要解锁一个操作你需要传递相应的的门票。如果传递错误的门票，那么可能会抛出一个异常，或者其他意想不到的错误。

另外一个值得关注的重要问题是，不像ReadWriteLock，StampedLocks是不可重入的。
因此尽管StampedLocks可能更快，但可能产生死锁。在实践中，这意味着你应该始终确保锁以及对应的门票不要逃逸出所在的代码块。

StampedLock是并发包里面jdk8版本新增的一个锁，该锁提供了三种模式的读写控制，三种模式分别如下：


* 写锁writeLock，是个排它锁或者叫独占锁，同时只有一个线程可以获取该锁，当一个线程获取该锁后，其它请求的线程必须等待，当目前没有线程持有读锁或者写锁的时候才可以获取到该锁，请求该锁成功后会返回一个stamp票据变量用来表示该锁的版本，当释放该锁时候需要unlockWrite并传递参数stamp。
* 悲观读锁readLock，是个共享锁，在没有线程获取独占写锁的情况下，同时多个线程可以获取该锁，如果已经有线程持有写锁，其他线程请求获取该读锁会被阻塞。这里讲的悲观其实是参考数据库中的乐观悲观锁的，这里说的悲观是说在具体操作数据前悲观的认为其他线程可能要对自己操作的数据进行修改，
所以需要先对数据加锁，这是在读少写多的情况下的一种考虑,请求该锁成功后会返回一个stamp票据变量用来表示该锁的版本，当释放该锁时候需要unlockRead并传递参数stamp。
* 乐观读锁tryOptimisticRead，是相对于悲观锁来说的，在操作数据前并没有通过CAS设置锁的状态，如果当前没有线程持有写锁，则简单的返回一个非0的stamp版本信息，获取该stamp后在具体操作数据前还需要调用validate验证下该stamp是否已经不可用，
也就是看当调用tryOptimisticRead返回stamp后到到当前时间间是否有其他线程持有了写锁，如果是那么validate会返回0，否者就可以使用该stamp版本的锁对数据进行操作。由于tryOptimisticRead并没有使用CAS设置锁状态所以不需要显示的释放该锁。
该锁的一个特点是适用于读多写少的场景，因为获取读锁只是使用与或操作进行检验，不涉及CAS操作，所以效率会高很多，但是同时由于没有使用真正的锁，在保证数据一致性上需要拷贝一份要操作的变量到方法栈，并且在操作数据时候可能其他写线程已经修改了数据，
而我们操作的是方法栈里面的数据，也就是一个快照，所以最多返回的不是最新的数据，但是一致性还是得到保障的。

使用乐观读锁还是很容易犯错误的，必须要小心，必须要保证如下的使用顺序：

```java
long stamp = lock.tryOptimisticRead(); //非阻塞获取版本信息
copyVaraibale2ThreadMemory();//拷贝变量到线程本地堆栈
if(!lock.validate(stamp)){ // 校验
    long stamp = lock.readLock();//获取读锁
    try {
        copyVaraibale2ThreadMemory();//拷贝变量到线程本地堆栈
     } finally {
       lock.unlock(stamp);//释放悲观锁
    }

}

useThreadMemoryVarables();//使用线程本地堆栈里面的数据进行操作
```


### StampedLock如何使用

```java
public class StampedLockDemo1 {
	// Java8引入，StampedLock可以认为是读写锁的改进版本，采用乐观加锁机制
	private final static StampedLock s1 = new StampedLock();
	private static Point point = new Point();

	public static void main(String[] args) {
		// 写入线程
		Runnable mRunnable = new Runnable() {

			@Override
			public void run() {
				point.move(new Random().nextInt(100), new Random().nextInt(100));
			}
		};
		// 读取线程
		Runnable rRunnable = new Runnable() {

			@Override
			public void run() {
				point.distanceFromOrigin();
			}
		};
		// 写入
		for (int i = 0; i < 20; i++) {
			new Thread(mRunnable).start();
		}
		// 读取
		for (int i = 0; i < 20; i++) {
			new Thread(rRunnable).start();
		}
	}

	// 来自JDK文档
	public static class Point {
		private double x, y;

		public void move(double deltaX, double deltaY) {
			// 上一个排他锁
			long stamp = s1.writeLock();
			try {
				x = deltaX;
				y = deltaY;
			} finally {
				s1.unlockWrite(stamp);
			}
		}

		public void distanceFromOrigin() {
			// tryOptimisticRead方法尝试一个乐观读，返回一个邮戳，作为这一次锁获取的凭证
			long stamp = s1.tryOptimisticRead();
			// 将全部变量拷贝到方法体栈内
			double currentX = x, currentY = y;
			// 判断stamp是否在读过程发生期间被修改
			// 如果没有被更改，则读取有效
			// 如果stamp是不可用的，可以如CAS操作一样，循环使用乐观读
			// 或者升级锁的级别，升级为悲观锁
			if (!s1.validate(stamp)) { //重点
				// 获取悲观的读锁，进一步读取数据，此时线程可能被挂起【挂起使用的是Unsafe.park()方法】
				// park方法遇到线程中断会直接返回。可能存在park的线程再次进入循环，如果不能退出，将占用大量CPU资源
				stamp = s1.readLock();
				try {
					currentX = x;
					currentY = y;
				} finally {
					s1.unlockRead(stamp);
				}
			}
			System.out.println(currentX * currentX + currentY * currentY);

		}
	}
}
```

StampedLock 对现存的锁实现有巨大的改进，特别是在读线程越来越多的场景下：
    
    1. StampedLock有一个复杂的API，对于加锁操作，很容易误用其他方法;
    2. 当只有2个竞争者的时候，Synchronized是一个很好的通用的锁实现;
    3. 当线程增长能够预估，ReentrantLock是一个很好的通用的锁实现;
    4. 选择使用ReentrantReadWriteLock时，必须经过小心的适度的测试;所有重大的决定，必须在基于测试数据的基础上做决定;
    5. 无锁的实现比基于锁的算法有更好短吞吐量;

### CLH是什么？怎么实现

前情提要

1、SMP(Symmetric Multi-Processor)

      SMP（Symmetric Multi-Processing）对称多处理器结构，指服务器中多个CPU对称工作，每个CPU访问内存地址所需时间相同。其主要特征是共享，包含对CPU，内存，I/O等进行共享。

      SMP能够保证内存一致性，但这些共享的资源很可能成为性能瓶颈，随着CPU数量的增加，每个CPU都要访问相同的内存资源，可能导致内存访问冲突，

      可能会导致CPU资源的浪费。常用的PC机就属于这种。

2、NUMA(Non-Uniform Memory Access)

      非一致存储访问，将CPU分为CPU模块，每个CPU模块由多个CPU组成，并且具有独立的本地内存、I/O槽口等，模块之间可以通过互联模块相互访问，访问本地内存的速度将远远高于访问远地内存(系统内其它节点的内存)的速度，这也是非一致存储访问的由来。NUMA较好地解决SMP的扩展问题，

      当CPU数量增加时，因为访问远地内存的延时远远超过本地内存，系统性能无法线性增加。

CLH(Craig, Landin, and Hagersten  locks): 是一个自旋锁，能确保无饥饿性，提供先来先服务的公平性。

CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，它不断轮询前驱的状态，如果发现前驱释放了锁就结束自旋。

      当一个线程需要获取锁时：

      a.创建一个的QNode，将其中的locked设置为true表示需要获取锁
      b.线程对tail域调用getAndSet方法，使自己成为队列的尾部，同时获取一个指向其前趋结点的引用myPred
      c.该线程就在前趋结点的locked字段上旋转，直到前趋结点释放锁
      d.当一个线程需要释放锁时，将当前结点的locked域设置为false，同时回收前趋结点
      
 如下图，线程A需要获取锁，其myNode域为true，tail指向线程A的结点，然后线程B也加入到线程A后面，tail指向线程B的结点。
 然后线程A和B都在其myPred域上旋转，一旦它的myPred结点的locked字段变为false，它就可以获取锁。
 明显线程A的myPred locked域为false，此时线程A获取到了锁。
 
![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/concurrent/CLH.png)

实现

```java
public class CLHLock implements Lock {  
    AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());  
    ThreadLocal<QNode> myPred;  
    ThreadLocal<QNode> myNode;  
  
    public CLHLock() {  
        tail = new AtomicReference<QNode>(new QNode());  
        myNode = new ThreadLocal<QNode>() {  
            protected QNode initialValue() {  
                return new QNode();  
            }  
        };  
        myPred = new ThreadLocal<QNode>() {  
            protected QNode initialValue() {  
                return null;  
            }  
        };  
    }  
  
    @Override  
    public void lock() {  
        QNode qnode = myNode.get();  
        qnode.locked = true;  
        QNode pred = tail.getAndSet(qnode);  
        myPred.set(pred);  
        while (pred.locked) {  
        }  
    }  
  
    @Override  
    public void unlock() {  
        QNode qnode = myNode.get();  
        qnode.locked = false;  
        myNode.set(myPred.get());  
    }  
}
```

 CLH队列锁的优点是空间复杂度低（如果有n个线程，L个锁，每个线程每次只获取一个锁，那么需要的存储空间是O（L+n），n个线程有n个。myNode，L个锁有L个tail），CLH的一种变体被应用在了JAVA并发框架中。
 
 CLH在SMP系统结构下该方法是非常有效的。但在NUMA系统结构下，每个线程有自己的内存，如果前趋结点的内存位置比较远，自旋判断前趋结点的locked域，性能将大打折扣，一种解决NUMA系统结构的思路是MCS队列锁。

### MCS是什么？怎么实现

 MSC与CLH最大的不同并不是链表是显示还是隐式，而是线程自旋的规则不同:CLH是在前趋结点的locked域上自旋等待，而MSC是在自己的结点的locked域上自旋等待。正因为如此，它解决了CLH在NUMA系统架构中获取locked域状态内存过远的问题。

      MCS队列锁的具体实现如下：

      a. 队列初始化时没有结点，tail=null
      b. 线程A想要获取锁，于是将自己置于队尾，由于它是第一个结点，它的locked域为false
      c. 线程B和C相继加入队列，a->next=b,b->next=c。且B和C现在没有获取锁，处于等待状态，所以它们的locked域为true，尾指针指向线程C对应的结点
      d. 线程A释放锁后，顺着它的next指针找到了线程B，并把B的locked域设置为false。这一动作会触发线程B获取锁
      
 ![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/concurrent/MCS.jpg)

 实现
 
```java
public class MCSLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;

            // wait until predecessor gives up the lock
            while (qnode.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null))
                return;
            
            // wait until predecessor fills in its next field
            while (qnode.next == null) {
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    class QNode {
        boolean locked = false;
        QNode next = null;
    }
}
```

### StampedLock 源码

后转[StampedLock源码解析](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/sourcecode/StampedLock.md)






[参考1](https://www.cnblogs.com/llkmst/p/4895478.html) [参考2](http://ifeve.com/lock-based-vs-lock-free-concurren/#more-8038) [参考3](http://ifeve.com/jdk8%e4%b8%adstampedlock%e5%8e%9f%e7%90%86%e6%8e%a2%e7%a9%b6/#more-34646)

