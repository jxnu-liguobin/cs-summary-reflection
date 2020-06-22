/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 要求如下： 1) 队列有最大长度限制 2) 线程安全 3) 生产线程通过 put 方法往队列添加数据，当队列满时候挂起等待 4)消费线程通过 take
 *     方法往队列拿出数据，当队列空时挂起等待 5) 不使用第三方库和java.util.concurrent.BlockingQueue 接口下的实现类
 * @author Mr.Li
 * @param <E>
 */
public class Main9<E> implements BlockingQueue<E> {
    private final int Max_Length = 100; // 队列最大长度限制
    private Vector<E> vector = new Vector<E>(Max_Length); // 模拟队列存储元素的线程安全集合
    private ReentrantLock lock = new ReentrantLock(true); // 互斥锁
    private Condition notFull = lock.newCondition();
    private Condition notempty = lock.newCondition();

    /**
     * @description 主要得分点如下： 1)使用 lock 和 condition 的 signal 机制，或者使用synchronized 和 obj 的 wait/notify
     *     机制的，得 6 分 2)队列满和空时，相应方法线程都能正常挂起的，得 6 分，通过 Thread.sleep等其他较为原始的方式实现时，请酌情给分（不高于 2 分）
     *     3)同步块内队列存储的数据结构读写没有线程安全问题的，得 4分（如 JDK 通过读写分别操作头尾指针，隔离读和写的相互影响，写和读本身各自加锁）
     *     4)能对读锁和写锁进行分离提高性能，并且思路正确的，得 2 分 5) 使用链表等更适合添加删除操作的数据结构，而非数组，得2 分
     */
    @Override
    public void put(E e) throws InterruptedException {
        lock.lockInterruptibly(); // 获取锁
        int size = vector.size();
        try {
            if (size == Max_Length) notFull.await(); // 进入阻塞状态
            else {
                vector.add(e);
                notempty.signal(); // 唤醒一个等待线程
            }
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    @Override
    public E take() throws InterruptedException {
        lock.lockInterruptibly(); // 获取锁
        E e = null;
        try {
            if (vector.size() == 0) notempty.await(); // 进入阻塞状态
            else {
                e = vector.get(vector.size() - 1);
                notFull.signal(); // 唤醒一个等待线程
                return e;
            }
            return e;
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    @Override
    public E element() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E peek() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E poll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E remove() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(E e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean offer(E e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int remainingCapacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }
}
