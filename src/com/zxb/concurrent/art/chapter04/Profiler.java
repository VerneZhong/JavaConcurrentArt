package com.zxb.concurrent.art.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构
 *
 * @author Mr.zxb
 * @date 2018-11-26 10:38
 */
public class Profiler {
    /**
     * 第一次get()方法调用时会进行初始化（如果set方法没有被调用），每个线程会调用一次
     */
    public static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };


    public static final void begin() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {

        Profiler.begin();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Cost:" + Profiler.end() + " mills.");
    }
}
