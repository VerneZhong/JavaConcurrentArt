package com.zxb.concurrent.art.chapter04;

/**
 * 线程池接口定义
 * @author Mr.zxb
 * @date 2018-11-26 17:17
 */
public interface ThreadPool<T extends Runnable> {

    /**
     * 执行一个任务
     * @param t
     */
    void execute(T t);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 增加工作线程
     * @param num
     */
    void addWorkers(int num);

    /**
     * 减少工作线程
     * @param num
     */
    void removeWorker(int num);

    /**
     * 得到正在等待执行的任务数量
     * @return
     */
    int getJobSize();
}
