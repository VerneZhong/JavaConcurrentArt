package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * Thread join()，其含义是：当前线程等待线程终止后，才从thread.join()方法返回
 *
 * @author Mr.zxb
 * @date 2018-11-26 10:10
 */
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread current = Thread.currentThread();
        // 每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程终止后，才从join()方法返回
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Damino(current), String.valueOf(i));
            thread.start();
            current = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Damino implements Runnable {

        private Thread thread;

        public Damino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
