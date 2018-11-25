package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 线程状态，共6种
 * NEW：初始状态，线程被创建，但还还没有调用start()方法
 * RUNNABLE：运行状态，Java线程将操作系统中的就绪和运行两种状态统称为“运行中”
 * BLOCKED：阻塞状态，表示线程阻塞于锁
 * WAITING：等待状态，表示线程进入等待状态， 进入该状态表示当前线程需要等待其他线程做出一个特定动作（通知或中断）
 * TIME_WAITING：超时等待状态，该状态不同于WAITING，它是可以在指定的时间自行返回的
 * TERMINATED：终止状态，表示当前线程已经执行完毕
 *
 * @author Mr.zxb
 * @date 2018-11-25 13:50:18
 */
public class ThreadState {

    public static void main(String[] args) {

        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    /**
     * 该线程不断的进行休眠
     */
    static class TimeWaiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(TimeUnit.SECONDS, 100);
            }
        }
    }

    /**
     * 该线程在Waiting.class实例上等待
     */
    static class Waiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程在Blocked.class实例上加锁后，不会释放资源
     */
    static class Blocked implements Runnable {

        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(TimeUnit.SECONDS, 100);
                }
            }
        }
    }

    static class SleepUtils {
        public static void second(TimeUnit timeUnit, int time) {
            try {
                timeUnit.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
