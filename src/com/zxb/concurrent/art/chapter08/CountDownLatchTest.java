package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * CountDownLatch
 * @author Mr.zxb
 * @date 2018-12-06 16:15
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            System.out.println("t1 is mission accomplished");
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 is mission accomplished");
            latch.countDown();
        });

        t1.start();
        t2.start();

        latch.await();
        System.out.println("main is mission accomplished");
    }
}
