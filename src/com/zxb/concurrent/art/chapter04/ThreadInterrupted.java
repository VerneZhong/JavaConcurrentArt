package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 * 线程sleep方法在抛出InterruptedException异常之前，JVM会先将该线程的中断标识位清除，然后抛出InterruptedException异常，
 * 此时调用isInterrupted()方法将会返回false
 * @author Mr.zxb
 * @date 2018-11-25 14:31:14
 */
public class ThreadInterrupted {

    public static void main(String[] args) throws InterruptedException {
        // sleep线程不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        // busy线程不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        // 休眠5秒，让sleep和busy线程充分运行
        TimeUnit.SECONDS.sleep(5);

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("sleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("busyThread interrupted is " + busyThread.isInterrupted());

        // 防止sleep和busy立刻退出
        TimeUnit.SECONDS.sleep(2);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " will be interrupted.");
                }
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {

            }
        }
    }
}
