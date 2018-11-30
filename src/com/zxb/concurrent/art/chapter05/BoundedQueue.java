package com.zxb.concurrent.art.chapter05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界队列，有界队列是一种特殊的队列，当队列为空时，队里的获取操作将会阻塞获取线程，知道队列中有新增的元素，
 * 当队列已满时，队列的插入操作将会阻塞当前插入线程，知道队列有空位
 * @author Mr.zxb
 * @date 2018-11-30 09:08
 */
public class BoundedQueue<T> {

    /**
     * 非公平锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * empty Condition
     */
    private Condition empty = lock.newCondition();

    /**
     * full Condition
     */
    private Condition full = lock.newCondition();

    /**
     * 新增index
     */
    private int addIndex;

    /**
     * 移除index
     */
    private int removeIndex;

    /**
     * 数组当前数量
     */
    private int count;

    private Object[] items;

    public BoundedQueue(int size) {
        this.items = new Object[size];
    }

    /**
     * 入列
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                full.await();
            }
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            items[addIndex] = t;
            ++count;
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出列
     * @return
     * @throws InterruptedException
     */
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                empty.await();
            }
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            T t = (T) items[removeIndex];
            --count;
            full.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}
