package com.zxb.concurrent.art.chatper02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于CAS线程安全的计数器和非线程安全的计数器
 *
 * @author Mr.zxb
 * @date 2018-11-14 20:54:18
 */
public class CASCounter {

    private AtomicInteger integer = new AtomicInteger(0);

    private int i = 0;

    public static void main(String[] args) {

        final CASCounter cas = new CASCounter();

        List<Thread> threads = new ArrayList<>(600);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    cas.count();
                    cas.safeCount();
                }
            }));
        }

        threads.forEach(Thread::start);

        // 等待所有线程执行完成
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("i = " + cas.i);
        System.out.println("atomic = " + cas.integer.get());
        System.out.println("time = " + (System.currentTimeMillis() - start));
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
//        integer.getAndIncrement();
        for (; ; ) {
            int i = integer.get();
            if (integer.compareAndSet(i, i++)) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }
}
