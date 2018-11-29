package com.zxb.concurrent.art.chapter05;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 公平锁和非公平锁在获取锁时的区别
 *
 * @author Mr.zxb
 * @date 2018-11-29 17:21
 */
public class FairAndUnfairTest {

    private static CountDownLatch countDownLatch;

    private static Lock failLock = new ReentrantLock2(true);

    private static Lock unfailLock = new ReentrantLock2(false);

    private static class Job extends Thread {

        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ", Waiting by " + ((ReentrantLock2) lock).getQueueThreads());
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void fail() {
        testLock(failLock);
    }

    @Test
    public void unfail() {
        testLock(unfailLock);
    }

    private void testLock(Lock lock) {
        countDownLatch = new CountDownLatch(1);
        IntStream.range(0, 5).mapToObj(i -> new Job(lock)).forEach(Thread::start);
        countDownLatch.countDown();
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean b) {
            super(b);
        }

        public Collection<Thread> getQueueThreads() {
            List<Thread> threads = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(threads);
            return threads;
        }
    }

}