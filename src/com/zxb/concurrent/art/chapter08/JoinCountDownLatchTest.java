package com.zxb.concurrent.art.chapter08;

/**
 * Thread Join 模拟 CountDownLatch
 * Join用于让当前执行线程等待join线程执行结束。其实现原理是不停检查join线程是否存活。如果存活就让当前线程永远等待
 * @author Mr.zxb
 * @date 2018-12-06 16:12
 */
public class JoinCountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("t1 is mission accomplished");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 is mission accomplished");
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("main is mission accomplished");
    }
}
