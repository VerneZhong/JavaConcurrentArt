package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier同步屏障是可循环使用的屏障。它让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障时，屏障彩虹打开，所有被
 * 屏障拦截的线程才会继续运行
 * @author Mr.zxb
 * @date 2018-12-06 16:18
 */
public class CyclicBarrierTest {

    private static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("t1 is mission accomplished");
        });

        Thread t2 = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("t2 is mission accomplished");
        });

        t1.start();
        t2.start();

        barrier.await();
        System.out.println("main is mission accomplished");
    }
}
