package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CountDownLatch计数器只能使用一次，而CyclicBarrier计数器可以reset()方法重置，能处理更为复杂的业务。
 * CyclicBarrier还提供了其他方法
 * getNumberWaiting()：可以获取CyclicBarrier阻塞的线程数量
 * isBroken()：用来了解阻塞的线程是否被中断。
 *
 * @author Mr.zxb
 * @date 2018-12-06 16:42
 */
public class CyclicBarrierTest3 {

    private static CyclicBarrier barrier = new CyclicBarrier(2);

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {

            } catch (BrokenBarrierException e) {

            }
        });

        t.start();
        t.interrupt();

        try {
            barrier.await();
        } catch (Exception e) {
            System.out.println("线程被中断:" + barrier.isBroken());
        }
    }
}
