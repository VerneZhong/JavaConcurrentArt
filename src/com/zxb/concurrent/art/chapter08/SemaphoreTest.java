package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore信号量是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源
 *
 * @author Mr.zxb
 * @date 2018-12-06 17:10
 */
public class SemaphoreTest {

    /**
     * 线程数量
     */
    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    /**
     * 最大限制5个线程访问
     */
    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                try {
                    // 获取许可证
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("save data.");
                    // 释放许可证
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }
}
