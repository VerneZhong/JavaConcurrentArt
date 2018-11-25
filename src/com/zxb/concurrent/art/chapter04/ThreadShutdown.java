package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 安全的终止线程，通过Boolean变量或是中断状态来控制线程的运行状态
 * @author Mr.zxb
 * @date 2018-11-25 21:37:01
 */
public class ThreadShutdown {

    public static void main(String[] args) throws InterruptedException {

        Runner runner = new Runner();
        Thread countThread = new Thread(runner, "CountThread");
        countThread.start();

        // 睡眠1秒，main线程对CountThread进行中断，使CountThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);

        countThread.interrupt();

        Runner runner1 = new Runner();
        countThread = new Thread(runner1, "CountThread");
        countThread.start();

        // 睡眠1秒，main线程对CountThread进行关闭，使CountThread能够感知on为false而结束
        TimeUnit.SECONDS.sleep(1);
        runner1.cancel();
    }

    static class Runner implements Runnable {

        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        /**
         * 关闭线程
         */
        public void cancel() {
            this.on = false;
        }
    }
}
