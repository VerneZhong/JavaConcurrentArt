package com.zxb.concurrent.art.chapter03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁内存语义的实现
 * ReentrantLock的实现依赖于Java同步器框架AbstractQueuedSynchronizer（简称AQS），AQS使用一个整型的volatile变量（命名为state）
 * 来维护同步状态
 * ReentrantLock又分为公平锁和非公平锁，ReentrantLock默认使用非公平锁
 * 公平锁和非公平锁释放时，最后都要写一个volatile变量state
 * 公平锁获取时，首先会读取volatile变量。
 * 非公平锁获取时，首先会使用CAS更新volatile变量，这个操作同时具有volatile读和写的内存语义
 *
 * @author Mr.zxb
 * @date 2018-11-21 22:13:02
 */
public class ReentrantLockExample {

    int a = 0;
    ReentrantLock lock = new ReentrantLock(false);

    public void writer() {
        // 获取锁
        lock.lock();
        try {
            a++;
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    public void reader() {
        lock.lock();
        try {
            int i = a;
            System.out.println(i);
        } finally {
            lock.unlock();
        }
    }
}
