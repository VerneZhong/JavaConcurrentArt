package com.zxb.concurrent.art.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 死锁案例，多个锁交叉会导致死锁
 * 避免死锁的几种方法：
 *          避免一个线程同时获取多个锁，
 *          避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源，
 *          尝试使用定时锁，使用lock.tryLock(timeout)来替代使用内部锁机制，
 *          对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况。
 * @author Mr.zxb
 * @date 2018-11-13 21:13:11
 */
public class DeadLockDemo {

    private static Lock lock = new Lock();

    private String A = "A";

    private String B = "B";

    public static void main(String[] args) {

        new DeadLockDemo().deadLock();
    }

    private void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    System.out.println("获取A锁");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println("获取B锁");
                }
                System.out.println("释放B锁");
            }
            System.out.println("释放A锁");
        });

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                try {
                    System.out.println("获取B锁");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    System.out.println("获取A锁");
                }
                System.out.println("释放A锁");
            }
            System.out.println("释放B锁");
        });

        t1.start();
        t2.start();
    }
}
