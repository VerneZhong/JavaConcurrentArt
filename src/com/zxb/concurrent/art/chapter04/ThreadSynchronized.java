package com.zxb.concurrent.art.chapter04;

/**
 * 线程间通信，volatile和synchronized，可以用javap -v class 编译指令
 * 同步块的实现是使用了monitorenter和monitorexit指令，而同步方法则是依靠方法修饰符上的ACC_SYNCHRONIZED来完成。
 * 无论采用哪种方式，其本质是对一个对象的监视器（monitor）获取，而这个获取是是排他的，也就是同一个时刻只有一个线程
 * 获取由synchronized所保护对象的监视器
 * @author Mr.zxb
 * @date 2018-11-25 21:52:20
 */
public class ThreadSynchronized {

    public static void main(String[] args) {

        // 对ThreadSynchronized Class对象加锁
        synchronized (ThreadSynchronized.class) {

        }
        // 静态同步方法对ThreadSynchronized Class对象加锁
        m();
    }

    public static synchronized void m() {

    }
}
