package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 * @author Mr.zxb
 * @date 2018-11-25 14:10:42
 */
public class ThreadDaemon {

    public static void main(String[] args) {

        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 守护线程的finally并不一定会执行
     */
    static class DaemonRunner implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonRunner finally run.");
            }
        }
    }
}
