package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier还提供了构造函数CyclicBarrier(int parties, Runnable barrierAction)，用于在线程到达屏障时，优先执行barrierAction
 * ，方便处理更复杂的业务场景
 * @author Mr.zxb
 * @date 2018-12-06 16:23
 */
public class CyclicBarrierTest2 {

    private static CyclicBarrier barrier = new CyclicBarrier(2, new A());

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        // A线程优先执行

        new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("thread is running.");
        }).start();

        barrier.await();
        System.out.println("main is running.");
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println("A thread is running.");
        }
    }
}
