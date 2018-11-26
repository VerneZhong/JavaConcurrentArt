package com.zxb.concurrent.art.chapter04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Mr.zxb
 * @date 2018-11-26 17:20
 */
public class DefaultThreadPool<T extends Runnable> implements ThreadPool<T> {

    /**
     * 线程池最大限制数
     */
    public static final int MAX_WORKER_NUMBERS = 10;

    /**
     * 线程池默认的数量
     */
    public static final int DEFAULT_WORKER_NUMBERS = 5;

    /**
     * 线程池最小的数量
     */
    public static final int MIN_WORKER_NUMBERS = 2;

    /**
     * 任务队列
     */
    public final LinkedList<T> jobs = new LinkedList<>();

    /**
     * 工作者列表
     */
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    /**
     * 工作者线程的数量
     */
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    /**
     * 线程编号生成
     */
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int workerNum) {
        this.workerNum = workerNum > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : workerNum < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : workerNum;
        initializeWorkers(workerNum);
    }

    /**
     * 初始化工作线程
     *
     * @param defaultWorkerNumbers
     */
    private void initializeWorkers(int defaultWorkerNumbers) {
        for (int i = 0; i < defaultWorkerNumbers; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(T t) {
        if (t != null) {
            // 添加一个工作，然后进行通知
            synchronized (jobs) {
                jobs.addLast(t);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            // 限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            // 按照给定的数量停止Worker
            int count = 0;
            while (count <= num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 工作者，负责消费任务
     */
    class Worker implements Runnable {

        /**
         * 是否工作
         */
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Runnable runnable;
                synchronized (jobs) {
                    // 如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部对Worker-Thread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    runnable = jobs.removeFirst();
                }
                if (runnable != null) {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        // 忽略Runnable执行中的Exception
                    }
                }
            }
        }

        /**
         * 关闭线程
         */
        public void shutdown() {
            this.running = false;
        }
    }
}
