package com.zxb.concurrent.art.chapter03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁内存语义的实现
 * ReentrantLock的实现依赖于Java同步器框架AbstractQueuedSynchronizer（简称AQS），AQS使用一个整型的volatile变量（命名为state）
 * 来维护同步状态
 * @author Mr.zxb
 * @date 2018-11-21 22:13:02
 */
public class ReentrantLockExample {

    int a = 0;
    ReentrantLock lock = new ReentrantLock();

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
