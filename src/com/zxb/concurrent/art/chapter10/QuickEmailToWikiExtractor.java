package com.zxb.concurrent.art.chapter10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 生产者和消费者案列，通过阻塞队列实现生产者与消费者解耦
 * @author Mr.zxb
 * @date 2018-12-10 16:32
 */
public class QuickEmailToWikiExtractor {

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool;

    /**
     * 核心线程的数量
     */
    private int corePoolSize;

    /**
     * 阻塞队列
     */
    private ArrayBlockingQueue<String> arrayBlockingQueue;

    public QuickEmailToWikiExtractor() {
        this.arrayBlockingQueue = new ArrayBlockingQueue<>(2000);
        // cpu核心数*2的线程数
        this.corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        this.threadPool = new ThreadPoolExecutor(corePoolSize, corePoolSize, 101, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void extract() throws InterruptedException {
        threadPool.execute(new ExtractEmailTask());
        insertToWiki();
    }

    private void insertToWiki() throws InterruptedException {
        // 登陆
        while (true) {
            // 2秒内获取不到就退出
            String email = arrayBlockingQueue.poll(2, TimeUnit.SECONDS);
            if (email == null) {
                break;
            }
            threadPool.execute(new insertToWikiTask());
        }
    }

    public List<String> extractEmail() {
        // 模拟获取数据
        List<String> allList = new ArrayList<>();

        if (allList.size() > 0) {
            allList.forEach(s -> arrayBlockingQueue.offer(s));
        }
        return null;
    }

    public class ExtractEmailTask implements Runnable {

        @Override
        public void run() {
            extractEmail();
        }
    }

    public class insertToWikiTask implements Runnable {

        @Override
        public void run() {
            // 插入数据
        }
    }
}
